package com.devcourse.web2_1_dashbunny_be.config;


import com.devcourse.web2_1_dashbunny_be.feature.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    //로그인없이 상용하기 위해 임시로 만들었습니다
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
}

