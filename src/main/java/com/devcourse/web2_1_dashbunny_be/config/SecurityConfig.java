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
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final PasswordEncoder passwordEncoder;

    // 인증 제공자
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 주소
//        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://frontend.example.com")); // 여러 출처 허용
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-CSRF-TOKEN", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 보안 필터 체인
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // CSRF 보호 비활성화 (실제 운영 환경에서는 활성화 권장)
                .csrf(AbstractHttpConfigurer::disable)
                // 인증 제공자 설정
                .authenticationProvider(authenticationProvider())

                // 요청에 대한 권한 설정
                // 권한순서는 위에서부터 아래로 내려감
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user/upload-profile-picture").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/test").hasRole("ADMIN")
                        .requestMatchers("/api/owner/**").hasRole("OWNER")
                        .requestMatchers("/api/auth/session-user").hasRole("USER")
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/uploads/upload-profile-picture").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(
                                "/api/auth/**",
                                "/login",
                                "/main",
                                "/error/**",
//                                "/test",
                                "/error",
                                "/favicon.ico",
                                "/images/**",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

//                .authorizeHttpRequests(authorize -> authorize
//                        // 모든 요청에 대해 인증 없이 접근 허용
//                        .anyRequest().permitAll()
//                )

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

//                // 보안 헤더 설정
//                .headers(headers -> headers
//                        .contentSecurityPolicy("default-src 'self'")
//                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
//                        .httpStrictTransportSecurity(hsts -> hsts
//                                .maxAgeInSeconds(31536000)
//                                .includeSubDomains(true)
//                        )
//                );

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