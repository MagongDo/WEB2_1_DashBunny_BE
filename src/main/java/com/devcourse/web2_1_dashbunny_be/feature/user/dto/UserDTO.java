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

    /**
     * User 엔티티를 UserDTO로 변환하는 정적 메서드
     *
     * @param user 변환할 User 엔티티
     * @return 변환된 UserDTO
     */
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .phone(user.getPhone())
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
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
