package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
	private String accessToken;
	private String refreshToken;
}
