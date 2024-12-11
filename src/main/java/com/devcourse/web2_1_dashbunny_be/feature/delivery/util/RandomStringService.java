package com.devcourse.web2_1_dashbunny_be.feature.delivery.util;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomStringService {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int STRING_LENGTH = 8;
	private final SecureRandom secureRandom = new SecureRandom();

	/**
	 * 완전 랜덤한 8자리 문자열 생성
	 * @return 랜덤 문자열
	 */
	public String generateRandomString() {
		StringBuilder randomString = new StringBuilder(STRING_LENGTH);
		for (int i = 0; i < STRING_LENGTH; i++) {
			int randomIndex = secureRandom.nextInt(CHARACTERS.length());
			randomString.append(CHARACTERS.charAt(randomIndex));
		}
		return randomString.toString();
	}
}

