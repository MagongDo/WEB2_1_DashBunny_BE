package com.devcourse.web2_1_dashbunny_be.feature.user.Util;

import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class SecurityUtil {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Object getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
//            System.out.println("Principal class: " + principal.getClass().getName());
//            System.out.println("Principal : " + principal);
            if (principal instanceof User) {
                User user = (User) principal;
                return user;
            }
            // OAuth2 카카오 로그인 사용자 처리
            else if (principal instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) principal;
                // getName()으로 Name 값 가져오기
                // provider_id 가져옴
                // String name = oauth2User.getName();

                return oauth2User; // Name 값 반환
            }
        }
        return null;
    }
}

