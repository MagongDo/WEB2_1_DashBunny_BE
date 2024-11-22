package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;

import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SocialUserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final PasswordEncoder passwordEncoder;


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




}
