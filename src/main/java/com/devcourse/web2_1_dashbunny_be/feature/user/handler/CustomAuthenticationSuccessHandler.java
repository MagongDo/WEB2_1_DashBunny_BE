package com.devcourse.web2_1_dashbunny_be.feature.user.handler;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String username = authentication.getName(); // 사용자 이름
        log.info("로그인 성공: 사용자 이름 - {}", username);
        User user = userService.getCurrentUser();
        String redirectUrl = "/main";

        if (user.getRole().equals("ROLE_ADMIN")) {
            redirectUrl = "/admin";
        } else if (user.getRole().equals("ROLE_OWNER")) {
            redirectUrl = "/owner";
        } else if (user.getRole().equals("ROLE_USER")) {
            redirectUrl = "/main";
        }

        // 로그인 성공 후, 기본 URL로 리다이렉트
        response.sendRedirect(redirectUrl);
    }

}

