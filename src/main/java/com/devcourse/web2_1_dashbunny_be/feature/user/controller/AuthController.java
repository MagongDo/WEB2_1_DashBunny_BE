package com.devcourse.web2_1_dashbunny_be.feature.user.controller;


import com.devcourse.web2_1_dashbunny_be.domain.user.PasswordResetRequest;
import com.devcourse.web2_1_dashbunny_be.domain.user.SmsVerification;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    /**
     * 사용자 회원가입을 하는 엔드포인트입니다. 코드 201(CREATED), 400(BAD_REQUEST)
     *
     * @param userDTO UserDTO 가 포함된 요청 본문
     *                   - phone;    // 휴대폰 번호 ex) 01012345678
     *                   - password; // 비밀번호
     *                   - name;     // 이름
     *                   - birthday; // 생년월일 ex) 000101-3
     * @return 회원가입 성공 메시지 또는 에러 메시지를 포함한 ResponseEntity
     */
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@Validated @RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 사장님 회원가입을 하는 엔드포인트입니다. 코드 201(CREATED), 400(BAD_REQUEST)
     *
     * @param userDTO UserDTO 가 포함된 요청 본문
     *                   - phone;    // 휴대폰 번호 ex) 01012345678
     *                   - password; // 비밀번호
     *                   - name;     // 이름
     *                   - birthday; // 생년월일 ex) 000101-3
     * @return 회원가입 성공 메시지 또는 에러 메시지를 포함한 ResponseEntity
     */
    @PostMapping("/signUp-owner")
    public ResponseEntity<String> signUpOwner(@Validated @RequestBody UserDTO userDTO) {
        try {
            userService.registerOwner(userDTO);
            return new ResponseEntity<>("사장님 회원가입 성공", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

        String phoneNum = smsVerification.getPhone();
        log.info("전화번호로 SMS 전송 요청을 받았습니다: {}", phoneNum);

        if (bindingResult.hasErrors()) {
            // 검증 실패 시 에러 메시지 반환
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return userService.sendSmsToFindPhone(phoneNum);
    }

    /**
     * 사용자의 전화번호와 인증 코드를 검증하는 엔드포인트입니다.
     *
     * @param smsVerification 전화번호와 인증 코드가 포함된 요청 본문
     * @return 검증 결과를 포함한 ResponseEntity
     */
    @PostMapping("/verify-sms")
    public ResponseEntity<Map<String, Object>> verifySms(@RequestBody SmsVerification smsVerification) {
        Map<String, Object> response = new HashMap<>();

        boolean isValid = userService.verifyCode
                (smsVerification.getPhone(), smsVerification.getVerificationCode());

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


    @GetMapping("/test")
    public ResponseEntity<User> getTest() {
        User a = userService.getCurrentUser();
        System.out.println("getCurrentUser : " + a);
        return ResponseEntity.ok(userService.getCurrentUser());
    }




}