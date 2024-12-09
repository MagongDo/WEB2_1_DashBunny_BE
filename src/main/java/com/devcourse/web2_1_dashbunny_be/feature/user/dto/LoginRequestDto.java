package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestDto {
	private String phone;
	private String password;
}
