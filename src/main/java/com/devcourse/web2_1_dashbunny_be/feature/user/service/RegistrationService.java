/*
package com.devcourse.web2_1_dashbunny_be.feature.user.service;



import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User users) {
        // 비밀번호 인코딩
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        // 생성일 설정
        users.setCreatedDate(LocalDateTime.now());

        // 기본 역할 설정
        if (users.getRole() == null || users.getRole().isEmpty()) {
            users.setRole("ROLE_USER");
        }

        return userRepository.save(users);
    }
}*/
