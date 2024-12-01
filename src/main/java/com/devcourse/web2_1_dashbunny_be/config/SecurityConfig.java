package com.devcourse.web2_1_dashbunny_be.config;


import com.devcourse.web2_1_dashbunny_be.config.oauth2.OAuth2AuthenticationSuccessHandler;
import com.devcourse.web2_1_dashbunny_be.feature.user.handler.CustomAuthenticationFailureHandler;
import com.devcourse.web2_1_dashbunny_be.feature.user.handler.CustomAuthenticationSuccessHandler;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 허용할 도메인
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 메서드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 허용할 헤더
        configuration.setAllowCredentials(true); // 쿠키 허용 여부

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    // 인증 제공자
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

//    @Bean
//    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
//        return new OAuth2AuthenticationSuccessHandler();
//    }


    // 보안 필터 체인
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정
                .securityMatcher("/**") // CORS를 적용할 경로 매핑
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // CSRF 보호 비활성화 (실제 운영 환경에서는 활성화 권장)
                .csrf(AbstractHttpConfigurer::disable)
                // 인증 제공자 설정
//                .authenticationProvider(authenticationProvider())
//
//                // 요청에 대한 권한 설정
//                // 권한순서는 위에서부터 아래로 내려감
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/user/upload-profile-picture").permitAll()
//                        .requestMatchers("/api/auth/session-user").hasRole("USER")
//                        .requestMatchers("/api/user/**").hasRole("USER")
//                        .requestMatchers("/uploads/upload-profile-picture").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(
//                                "/api/auth/**",
//                                "/login",
//                                "/main",
//                                "/test",
//                                "/error",
//                                "/favicon.ico",
//                                "/images/**",
//                                "/css/**",
//                                "/js/**"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )

                .authorizeHttpRequests(authorize -> authorize
                        // 모든 요청에 대해 인증 없이 접근 허용
                        .anyRequest().permitAll()
                )

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                )

                // 폼 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login") // 로그인 폼을 제공하는 페이지 URL
                        .loginProcessingUrl("/loginForm") // 로그인 폼 제출 시 처리할 URL
                        .usernameParameter("phone") // 폼에서 사용하는 username 파라미터 이름
                        .passwordParameter("password") // 폼에서 사용하는 password 파라미터 이름
                        .defaultSuccessUrl("/main", true) // 로그인 성공 시 이동할 URL
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동할 URL
                        .successHandler(successHandler) // 성공 핸들러 등록
                        .failureHandler(failureHandler) // 실패 핸들러 등록
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                // 세션 관리 설정
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                        .sessionConcurrency(concurrency -> concurrency
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
                        )
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/")
                );

        return http.build();
    }

}