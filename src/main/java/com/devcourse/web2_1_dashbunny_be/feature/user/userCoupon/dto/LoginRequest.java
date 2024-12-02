package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String phone;
    private String password;
}
