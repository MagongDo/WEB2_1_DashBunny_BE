package com.devcourse.web2_1_dashbunny_be.feature.user.service;


import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        // 'phone'을 사용자명으로 사용한다고 가정.
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + phone));
        // is_withdrawn 검증
        if ("Y".equals(user.getIsWithdrawn())) {
            throw new UsernameNotFoundException("탈퇴한 회원입니다.");
        }

        return user;
    }
}

