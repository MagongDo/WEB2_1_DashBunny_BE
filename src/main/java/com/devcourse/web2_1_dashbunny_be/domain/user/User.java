package com.devcourse.web2_1_dashbunny_be.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Table(name = "users")
@Entity
@Getter
@Builder(toBuilder = true)
@ToString
@EntityListeners(AuditingEntityListener.class) // Date를 등록, 수정 일시 자동 반영 중요!!
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(nullable = false, length = 11)
    private String phone;

    @JsonIgnore
    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 8)
    private String birthday;

    @Column(length = 50)
    private String email;

    @Column(nullable = false, length = 11)
    private String role;

    @Column(length = 255)
    private String profileImageUrl;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Column(nullable = false, length = 1)
    @Builder.Default
    @Pattern(regexp = "[YN]")
    private String isWithdrawn = "N";

    @Column(nullable = false, length = 1)
    @Builder.Default
    @Pattern(regexp = "[YN]")
    private String isSocial = "N";

    @JsonIgnore
    @Setter(value = AccessLevel.PUBLIC)
    @Column(nullable = false, unique = true)
    private String refreshToken;

    @JsonIgnore
    @Setter(value = AccessLevel.PUBLIC)
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime refreshTokenExpiryDate =
            LocalDateTime.of(2000, 1, 1, 0, 0);



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}