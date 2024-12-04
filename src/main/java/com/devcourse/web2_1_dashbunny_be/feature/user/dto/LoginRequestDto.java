package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	private String phone;
	private String password;
}
