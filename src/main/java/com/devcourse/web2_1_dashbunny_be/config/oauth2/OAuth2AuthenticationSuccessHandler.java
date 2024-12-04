package com.devcourse.web2_1_dashbunny_be.config.oauth2;


import com.devcourse.web2_1_dashbunny_be.config.jwt.JwtUtil;
import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public OAuth2AuthenticationSuccessHandler(UserService userService, UserRepository userRepository, JwtUtil jwtUtil) {
      this.userService = userService;
			this.userRepository = userRepository;
			this.jwtUtil = jwtUtil;
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
                    log.info("새로운 OAuth2 사용자 등록");
                    System.out.println("세션 저장된 유저 정보 : " + request.getSession().getAttribute("AdditionalInfoUser"));
                    UserDto userDTO = (UserDto) request.getSession().getAttribute("AdditionalInfoUser");
                    user = userService.registerUser(userDTO);
                    socialUser = userService.registerSocialUser(oauth2User, provider, user);
                }else {
                    log.info("기존 사용자 조회");
                    user = userService.findUserByProviderId(providerId);
                    Long userId = user.getUserId();
                    userRepository.updateIsSocialToY(userId);
                }
                // 탈퇴 상태 확인
                if ("Y".equals(user.getIsWithdrawn())) {
                    log.warn("탈퇴한 사용자 로그인 시도. User ID: {}", user.getUserId());
                    response.sendRedirect("/login?error=탈퇴한 사용자 로그인 시도.");
                    return;
                }
                // Access Token 생성
                String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());

                // Refresh Token 생성 및 저장
                String refreshToken = jwtUtil.generateRefreshToken();
                user.setRefreshToken(refreshToken);
                user.setRefreshTokenExpiryDate(LocalDateTime.now().plusDays(7));
                userRepository.save(user);
                // 유저 아이디에 해당하는 user테이블의 is_social 을 Y로 변경 후 소셜유저에 저장
                Long userId = user.getUserId();
                userRepository.updateIsSocialToY(userId);

                // 클라이언트에 토큰 반환
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(String.format(
                        "{\"accessToken\": \"%s\", \"refreshToken\": \"%s\"}", accessToken, refreshToken));
                log.info("클라이언트에 토큰 반환");
                // 사용자 정보를 세션에 등록
//                HttpSession session = request.getSession(true);
////                session.setAttribute(SESSION_USER_KEY, socialUser);
//                session.setAttribute(SESSION_USER_KEY, user);
//                log.info("사용자가 세션에 '{}' 키로 등록되었습니다.", SESSION_USER_KEY);
//
//                // 세션에 저장된 사용자 정보 로그로 출력
//                log.info("세션에 저장된 사용자 정보: {}", session.getAttribute(SESSION_USER_KEY));
            }
        } catch (Exception e) {
            log.error("사용자 세션 등록 중 오류 발생", e);
            throw new ServletException("Authentication Success Handling Failed", e);
        }
        // 기본 인증 성공 동작 수행
        //super.onAuthenticationSuccess(request, response, authentication);
    }

}

