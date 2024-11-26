package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.user.SmsVerification;
import com.devcourse.web2_1_dashbunny_be.feature.user.Util.SmsUtil;
import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;

import com.devcourse.web2_1_dashbunny_be.feature.user.Util.ValidationUtil;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SmsVerificationRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SocialUserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmsUtil smsUtil;
    private final ValidationUtil validationUtil;
    private final SmsVerificationRepository smsVerificationRepository;


    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof SocialUser) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
    public User registerUser(UserDTO userDTO) throws Exception {

        if(userRepository.findByPhone(userDTO.getPhone()).isPresent()) {
            throw new Exception("이미 존재하는 전화번호입니다.");
        }

        User user = User.builder()
                .phone(userDTO.getPhone())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .name(userDTO.getName())
                .birthday(userDTO.getBirthday())
                .email(userDTO.getEmail())
                .role("ROLE_USER") // 기본 역할 설정
                .createdDate(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<SocialUser> findByProviderId(String providerId) {

        return socialUserRepository.findByProviderId(providerId);
    }


    public SocialUser registerSocialUser(OAuth2User oauth2User, String provider) {
        /**
         * provider - 파라미터
         * providerId - oauth2User
         * userId - 존재할 시
         * userName
         */
        String providerId = oauth2User.getName();

        return findByProviderId(providerId)
                .map(socialUser ->{
                    log.warn("이미 존재하는 사용자 socialUser : {}\nprovider: {}\nproviderId : {}", socialUser, provider, providerId);
                    return socialUser;
                })
                .orElseGet(() -> {
                    Map<String, Object> attributes = oauth2User.getAttributes();
                    log.info("findByProviderId - attributes: {}", attributes);

                    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
                    log.info("findByProviderId - properties: {}", properties);

                    String nickname = (String) properties.get("nickname");

                    SocialUser socialUser = SocialUser.builder()
                            .providerId(providerId)
                            .provider(provider)
                            .userName(nickname)
                            .createdDate(LocalDateTime.now())
                            .build();

                    return socialUserRepository.save(socialUser);
                });
    }

    // 인증번호 전송 서비스
    public ResponseEntity<?> sendSmsToFindPhone(String phone) {

        Map<String, Object> response = new HashMap<>();

        try {
            // 1. 전화번호 형식 정리
            String phoneNum = phone.replaceAll("-", "");
            log.info("정리된 전화번호: {}", phoneNum);

            // 2. 사용자 찾기 (필요 시 활성화)
            // 예시로 이름이 없이 전화번호만으로 찾는 경우
//            User foundUser = userRepository.findByPhone(phoneNum)
//                    .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));

            // 3. 인증 코드 생성
            String verificationCode = validationUtil.createCode(phoneNum);
            log.info("생성된 인증 코드: {}", verificationCode);

            // 4. SMS 전송
            SingleMessageSentResponse smsResponse = smsUtil.sendOne(phoneNum, verificationCode);
            log.info("SMS 전송 성공: {}", smsResponse);


            // 6. 성공 응답 설정
            response.put("code", 200);
            response.put("message", "SMS 전송 성공");
            return ResponseEntity.ok(response);

        } catch (NoSuchElementException e) {
            // 사용자 미발견 시 응답 설정
            log.error("사용자 미발견: {}", e.getMessage());
            response.put("code", 404);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("SMS 전송 오류: {}", e.getMessage());
            response.put("code", 500);
            response.put("message", "알 수 없는 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 인증번호 검증 서비스
    public boolean verifyCode(String phoneNumber, String code) {
        Optional<SmsVerification> optionalVerification =
                smsVerificationRepository.findTopByPhoneOrderByCreatedAtDesc(phoneNumber);

        if (optionalVerification.isPresent()) {
            SmsVerification verification = optionalVerification.get();

            // 5분 이내 확인
            if (verification.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(5))) {
                // 인증번호 일치 및 사용되지 않은 경우
                if (verification.getVerificationCode().equals(code) && !verification.getIsUsed()) {
                    // 인증번호 사용 처리
                    verification.setIsUsed(true);
                    smsVerificationRepository.save(verification);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 사용자의 프로필 사진 URL을 업데이트합니다.
     *
     * @param userId       사용자 ID
     * @param profileImageUrl 프로필 사진 URL
     */
    @Transactional
    public void updateProfileImageUrl(Long userId, String profileImageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // Builder 패턴을 사용하여 User 객체 생성
        User updatedUser = user.toBuilder()
                .profileImageUrl(profileImageUrl)
                .modifiedDate(LocalDateTime.now())
                .build();

        userRepository.save(updatedUser);
    }

}
