package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void registerUser() throws Exception {
//        for (int i = 1; i <= 100; i++) {
//            UserDTO userDTO = UserDTO.builder()
//                    .phone("010000000" + String.format("%02d", i)) // 전화번호 고유하게 설정
//                    .password("testPassword" + i) // 각 사용자마다 다른 비밀번호 설정
//                    .name("사용자")
//                    .birthday("19990515")
//                    .email("testUser" + i + "@example.com") // 이메일 고유하게 설정
//                    .build();
//
//            userService.registerUser(userDTO);
//        }
        for (int i = 101; i <= 1000; i++) { // 새로운 범위
            UserDTO userDTO = UserDTO.builder()
                    .phone("01000000" + String.format("%03d", i)) // 전화번호 고유하게 설정
                    .password("testPassword" + i) // 각 사용자마다 다른 비밀번호 설정
                    .name("사용자" + i) // 사용자 이름 고유하게 설정
                    .birthday("19990515")
                    .email("testUser" + i + "@example.com") // 이메일 고유하게 설정
                    .build();

            userService.registerUser(userDTO);
        }
    }
}