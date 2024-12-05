package com.devcourse.web2_1_dashbunny_be.config.oauth2;


import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Slf4j
@Component
//@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SESSION_USER_KEY = "SESSION_USER";
    private final UserService userService;

    public OAuth2AuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
        setDefaultTargetUrl("/api/main"); // 기본 리디렉션 URL 설정
        setAlwaysUseDefaultTargetUrl(true); // 항상 기본 URL로 리디렉션
    }
    /**
     * 사용자 세션에 등록
     * 사용자를 가입시키는 처리
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        // Authentication 객체는 이미 SecurityContextHolder에 설정됨
        Object principal = authentication.getPrincipal();

        log.info("OAuth2 authentication success: {}", principal);

        try {
            if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {

                OAuth2User oauth2User = oauth2Token.getPrincipal();
                String providerId = oauth2User.getName();
                log.info("oauth2User :  {}", oauth2User.getName());
                // providerId = 3800475876
                String provider = oauth2Token.getAuthorizedClientRegistrationId();
                // provider = kakao;

                SocialUser socialUser;
                User user;
                if(userService.findByProviderId(providerId).isEmpty()) {
                    System.out.println("세션 저장된 유저 정보 : " + request.getSession().getAttribute("AdditionalInfoUser"));
                    UserDTO userDTO = (UserDTO) request.getSession().getAttribute("AdditionalInfoUser");
                    user = userService.registerUser(userDTO);

                    socialUser = userService.registerSocialUser(oauth2User, provider, user);
                }else {
                    // 기존 사용자 조회
                    socialUser = userService.findByProviderId(providerId)
                            .orElseThrow(() -> new IllegalArgumentException("SocialUser not found"));
                    user = userService.findUserByProviderId(providerId);
                }
                // 탈퇴 상태 확인
                if ("Y".equals(user.getIsWithdrawn())) {
                    log.warn("탈퇴한 사용자 로그인 시도. User ID: {}", user.getUserId());
                    response.sendRedirect("/login?error=탈퇴한 사용자 로그인 시도.");
                    return;
                }
                // 사용자 정보를 세션에 등록
                HttpSession session = request.getSession(true);
//                session.setAttribute(SESSION_USER_KEY, socialUser);
                session.setAttribute(SESSION_USER_KEY, user);
                log.info("사용자가 세션에 '{}' 키로 등록되었습니다.", SESSION_USER_KEY);

                // 세션에 저장된 사용자 정보 로그로 출력
                log.info("세션에 저장된 사용자 정보: {}", session.getAttribute(SESSION_USER_KEY));

                // SecurityContext에 SocialUser 설정
                // SecurityContextHolder에 설정된 Authentication의 principal을 SocialUser로 대체
//                UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
//                        socialUser, authentication.getCredentials(), authentication.getAuthorities());
//
//                SecurityContextHolder.getContext().setAuthentication(newAuth);
//
//                log.info("SecurityContextHolder의 Authentication principal이 SocialUser로 설정되었습니다.");


            }
        } catch (Exception e) {
            log.error("사용자 세션 등록 중 오류 발생", e);
            throw new ServletException("Authentication Success Handling Failed", e);
        }
        // 기본 인증 성공 동작 수행
        super.onAuthenticationSuccess(request, response, authentication);
    }

}

