package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

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

}
