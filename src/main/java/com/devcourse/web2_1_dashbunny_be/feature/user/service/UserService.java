package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.user.SmsVerification;
import com.devcourse.web2_1_dashbunny_be.feature.user.Util.SecurityUtil;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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


    public User registerUser(UserDTO userDTO) throws Exception {

        if (userRepository.findByPhone(userDTO.getPhone()).isPresent()) {
            throw new Exception("이미 존재하는 전화번호입니다.");
        }

        // 비밀번호가 있는지 확인
        String rawPassword = userDTO.getPassword();
        String encodedPassword;

        if (rawPassword != null && !rawPassword.isEmpty()) {
            encodedPassword = passwordEncoder.encode(rawPassword);
        } else {
            // 비밀번호가 없으면 랜덤 비밀번호 생성
            String randomPassword = UUID.randomUUID().toString();
            encodedPassword = passwordEncoder.encode(randomPassword);
            log.info("비밀번호가 제공되지 않아 랜덤 비밀번호를 생성했습니다: {}", randomPassword);
            // 필요시, 사용자에게 랜덤 비밀번호를 이메일 등으로 전송하는 로직을 추가할 수 있습니다.
        }

        User user = User.builder()
                .phone(userDTO.getPhone())
                .password(encodedPassword)
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


    public SocialUser registerSocialUser(OAuth2User oauth2User, String provider, User user) {
        /**
         * provider - 파라미터
         * providerId - oauth2User
         * userId - 존재할 시
         * userName
         */
        String providerId = oauth2User.getName();

        return findByProviderId(providerId)
                .map(socialUser -> {
                    log.warn("이미 존재하는 사용자 socialUser : {}\nprovider: {}\nproviderId : {}", socialUser, provider, providerId);
                    return socialUser;
                })
                .orElseGet(() -> {
                    Map<String, Object> attributes = oauth2User.getAttributes();
                    log.info("findByProviderId - attributes: {}", attributes);

                    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
                    log.info("findByProviderId - properties: {}", properties);

                    String nickname = (String) properties.get("nickname");
                    // 유저 아이디에 해당하는 user테이블의 is_social 을 Y로 변경 후 소셜유저에 저장
                    Long userId = user.getUserId();
                    userRepository.updateIsSocialToY(userId);

                    SocialUser socialUser = SocialUser.builder()
                            .user(user)
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
            // 전화번호 형식 정리
            String phoneNum = phone.replaceAll("-", "");
            log.info("정리된 전화번호: {}", phoneNum);

            // 인증 코드 생성
            String verificationCode = validationUtil.createCode(phoneNum);
            log.info("생성된 인증 코드: {}", verificationCode);

            // SMS 전송
            SingleMessageSentResponse smsResponse = smsUtil.sendOne(phoneNum, verificationCode);
            log.info("SMS 전송 성공: {}", smsResponse);

            // 성공 응답 설정
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
     * @param userId          사용자 ID
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

    // 일반 로그인된 사용자 정보
    public User getCurrentUser() {
        Object currentUser = SecurityUtil.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalArgumentException("사용자가 인증되지 않음");
        }
        User user = null;
        String providerId;
        if (currentUser instanceof User) {
            user = (User) currentUser;
            // 사용자 찾기
            user = userRepository.findByPhone(user.getPhone())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        } else if (currentUser instanceof OAuth2User) { // OAuth2 카카오 로그인 사용자 처리
            OAuth2User oauth2User = (OAuth2User) currentUser;
            // getName()으로 Name 값 가져오기
            // provider_id 가져옴
            providerId = oauth2User.getName();
            user = findUserByProviderId(providerId);
        }
        return user;
    }

    // 비밀번호 변경
    public void resetPassword(String phone, String verificationCode, String newPassword) {
        // 인증번호 검증
        boolean isVerified = verifyCode(phone, verificationCode);
        if (!isVerified) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 인증 코드");
        }

        // 사용자 찾기
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("지정된 전화번호에 대한 사용자를 찾을 수 없습니다"));

        // 비밀번호 변경
        User setUserPw = user.toBuilder()
                .password(passwordEncoder.encode(newPassword))
                .build();

        userRepository.save(user);
    }

    // 닉네임 변경
    public void updateName(String newName) {
        // 현재 로그인된 사용자 확인
        User user = getCurrentUser();
        log.info("updateName : {} ",newName);
        log.info("updateName : {} ",user);
        // 닉네임 변경
        User setUserName = user.toBuilder()
                .name(newName)
                .build();
        userRepository.save(setUserName);
    }

    // 회원 탈퇴
    public void withdrawUser() {
        // 현재 로그인된 사용자 확인
        User user = getCurrentUser();
        // 회원 탈퇴 처리
        user = user.toBuilder()
                .isWithdrawn("Y") // 탈퇴 상태로 변경
                .modifiedDate(LocalDateTime.now()) // 수정 시간 갱신
                .build();

        userRepository.save(user);

        // 로그아웃 처리 (옵션)
        SecurityContextHolder.clearContext();
    }

    // providerId로 social_users 에서 userId 가져오고 users 테이블에서 user_id로 조회 하는 메소드
    public User findUserByProviderId(String providerId) {
        // 1. provider_id로 social_users에서 user_id 가져오기
        SocialUser socialUser = socialUserRepository.findByProviderId(providerId)
                .orElseThrow(() -> new IllegalArgumentException("providerId에 대한 소셜 사용자를 찾을 수 없습니다: " + providerId));

        Long userId = socialUser.getUser().getUserId();

        // 2. users 테이블에서 user_id로 조회
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("userId에 대한 사용자를 찾을 수 없습니다: " + userId));
    }

    public User registerOwner(UserDTO userDTO) throws Exception {

        if(userRepository.findByPhone(userDTO.getPhone()).isPresent()) {
            throw new Exception("이미 존재하는 전화번호입니다.");
        }

        // 비밀번호가 있는지 확인
        String rawPassword = userDTO.getPassword();
        String encodedPassword;

        if (rawPassword != null && !rawPassword.isEmpty()) {
            encodedPassword = passwordEncoder.encode(rawPassword);
        } else {
            // 비밀번호가 없으면 랜덤 비밀번호 생성
            String randomPassword = UUID.randomUUID().toString();
            encodedPassword = passwordEncoder.encode(randomPassword);
            log.info("비밀번호가 제공되지 않아 랜덤 비밀번호를 생성했습니다: {}", randomPassword);
            // 필요시, 사용자에게 랜덤 비밀번호를 이메일 등으로 전송하는 로직을 추가할 수 있습니다.
        }

        User user = User.builder()
                .phone(userDTO.getPhone())
                .password(encodedPassword)
                .name(userDTO.getName())
                .birthday(userDTO.getBirthday())
                .email(userDTO.getEmail())
                .role("ROLE_OWNER") // 기본 역할 설정
                .createdDate(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

}
