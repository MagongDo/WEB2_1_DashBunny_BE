package com.devcourse.web2_1_dashbunny_be.feature.user.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }



//    // 회원가입 페이지 (선택 사항)
//    @GetMapping("/signUp")
//    public String register() {
//        return "signUp";
//    }
}
