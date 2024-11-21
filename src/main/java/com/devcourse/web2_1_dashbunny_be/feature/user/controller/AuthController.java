package com.devcourse.web2_1_dashbunny_be.feature.user.controller;


import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Validated @RequestBody UserDTO userDTO) {
        try {
            User user = userService.registerUser(userDTO);
            return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private static final String SESSION_USER_KEY = "SESSION_USER";

    @GetMapping("/session-user")
    public SocialUser getSessionUser(HttpSession session) {
        SocialUser socialUser = (SocialUser) session.getAttribute(SESSION_USER_KEY);
        return socialUser;
    }

}
