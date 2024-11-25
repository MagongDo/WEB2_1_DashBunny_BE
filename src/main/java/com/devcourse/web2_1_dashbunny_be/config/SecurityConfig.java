package com.devcourse.web2_1_dashbunny_be.config;


import com.devcourse.web2_1_dashbunny_be.config.oauth2.OAuth2AuthenticationSuccessHandler;
import com.devcourse.web2_1_dashbunny_be.feature.user.handler.CustomAuthenticationFailureHandler;
import com.devcourse.web2_1_dashbunny_be.feature.user.handler.CustomAuthenticationSuccessHandler;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.CustomUserDetailsService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    //로그인없이 상용하기 위해 임시로 만들었습니다

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

//    @Bean
//    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
//        return new OAuth2AuthenticationSuccessHandler();
//    }


    // 보안 필터 체인

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // 모든 요청 허용
                )
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .formLogin(form -> form.disable()) // Form 로그인 비활성화
                .httpBasic(basic -> basic.disable()); // HTTP Basic 인증 비활성화

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // 비밀번호 인코딩 생략
    }


//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
//    // 비밀번호 인코더 빈
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // 인증 제공자
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }
//
//    // 보안 필터 체인
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                // CSRF 보호 비활성화 (실제 운영 환경에서는 활성화 권장)
//                .csrf(AbstractHttpConfigurer::disable)
//
//                // 요청에 대한 권한 설정
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                // 폼 로그인 설정
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/home", true)
//                        .permitAll()
//                )
//
//                // 로그아웃 설정
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                )
//
//                // 세션 관리 설정
//                .sessionManagement(session -> session
//                        .sessionConcurrency(concurrency -> concurrency
//                                .maximumSessions(1)
//                                .maxSessionsPreventsLogin(false)
//                        )
//                );
//
//        return http.build();
//    }

                // CSRF 보호 비활성화 (실제 운영 환경에서는 활성화 권장)
                .csrf(AbstractHttpConfigurer::disable)
                // 인증 제공자 설정
                .authenticationProvider(authenticationProvider())

                // 요청에 대한 권한 설정
                // 권한순서는 위에서부터 아래로 내려감
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/session-user").hasRole("USER")
                        .requestMatchers(
                                "/api/auth/**",
                                "/login",
                                "/main",
                                "/test",
                                "/error",
                                "/favicon.ico",
                                "/images/**",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
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
                        .sessionFixation().changeSessionId()
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

