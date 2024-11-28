package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 11)
    private String phone;

    @NotBlank
    @Size(min = 6, max = 40) // 비밀번호 최소 길이 설정
    private String password;

    private String name;

    @Size(max = 8)
    private String birthday;

    @Email
    private String email;

    private String profileImageUrl;

    public UserDTO(User user) {
        this.phone = user.getPhone();
        this.password = user.getPassword();
        this.name = user.getName();
        this.birthday = user.getBirthday();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
    }

    public User toEntity() {
        return User.builder()
                .phone(phone)
                .password(password)
                .name(name)
                .birthday(birthday)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .build();
    }

}
