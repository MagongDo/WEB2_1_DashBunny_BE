package com.devcourse.web2_1_dashbunny_be.feature.user.controller;


import com.devcourse.web2_1_dashbunny_be.domain.user.SmsVerification;
import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 사용자의 전화번호로 인증 코드를 SMS로 전송하는 엔드포인트입니다.
     *
     * @param smsVerification 전화번호가 포함된 요청 본문
     * @return SMS 전송 결과를 포함한 ResponseEntity
     */
    @PostMapping("/send-one")
    public ResponseEntity<?> sendOne(
            @Validated @RequestBody SmsVerification smsVerification,
            BindingResult bindingResult) {

        String phoneNum = smsVerification.getPhoneNumber();
        log.info("전화번호로 SMS 전송 요청을 받았습니다: {}", phoneNum);

        if (bindingResult.hasErrors()) {
            // 검증 실패 시 에러 메시지 반환
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return userService.sendSmsToFindPhone(phoneNum);
    }

    @PostMapping("/verify-sms")
    public ResponseEntity<?> verifySms(@RequestBody SmsVerification smsVerification) {
        Map<String, Object> response = new HashMap<>();

        boolean isValid = userService.verifyCode
                (smsVerification.getPhoneNumber(), smsVerification.getVerificationCode());

        if (isValid) {
            response.put("code", 200);
            response.put("message", "인증 성공");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 400);
            response.put("message", "인증 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}
