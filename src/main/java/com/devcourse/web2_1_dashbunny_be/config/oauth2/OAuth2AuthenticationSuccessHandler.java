package com.devcourse.web2_1_dashbunny_be.config.oauth2;


import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SocialUserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SESSION_USER_KEY = "SESSION_USER";

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        Object principal = authentication.getPrincipal();
        log.info("OAuth2 authentication success: {}", principal);

        /**
         * 사용자 세션에 등록
         * 사용자를 가입시키는 처리
         */

        try {
            if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {


                OAuth2User oauth2User = oauth2Token.getPrincipal();
                String provider = oauth2Token.getAuthorizedClientRegistrationId();
                SocialUser socialUser = userService.registerSocialUser(oauth2User, provider);

                // 사용자 정보를 세션에 등록
                HttpSession session = request.getSession(true);
                session.setAttribute(SESSION_USER_KEY, socialUser);
                log.info("사용자가 세션에 '{}' 키로 등록되었습니다.", SESSION_USER_KEY);

                // 세션에 저장된 사용자 정보 로그로 출력
                SocialUser sessionUser = (SocialUser) session.getAttribute(SESSION_USER_KEY);
                log.info("세션에 저장된 사용자 정보: {}", sessionUser);
            }
        } catch (Exception e) {
            log.error("사용자 세션 등록 중 오류 발생", e);
            throw new ServletException("Authentication Success Handling Failed", e);
        }
        // 기본 인증 성공 동작 수행
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private SocialUser processUserOAuth2UserJoin(OAuth2User oauth2User, String provider) {
        return userService.registerSocialUser(oauth2User, provider);
    }

}

