package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MainController {

//    private final KakaoService kakaoService;
//
//    @Value("${kakao.client_id}")
//    private String client_id;
//
//    @Value("${kakao.redirect_uri}")
//    private String redirect_uri;

//    @GetMapping ("/login")
//    public String login(Model model) {
//
//        return "login";
//    }

    @GetMapping ("/test")
    public String test(Model model) {

//        String location =
//                "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
//        model.addAttribute("location", location);
        return "test";

    }

//    @GetMapping("/oauth/kakao")
//    public ResponseEntity<?> oauthKakao(@RequestParam("code") String code) {
//
//        String accessToken = kakaoService.getAccessTokenFromKakao(code);
//        log.info(accessToken);
//        KakaoUserInfoResponseDTO userInfo = kakaoService.getUserInfo(accessToken);
//        // User 로그인, 또는 회원가입 로직 추가
//        log.info(userInfo);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/main")
    public String main(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                model.addAttribute("user", user);
            }
        }
        return "main"; // main.html로 이동
    }

}
