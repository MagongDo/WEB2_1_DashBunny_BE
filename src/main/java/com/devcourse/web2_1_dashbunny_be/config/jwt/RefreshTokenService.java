package com.devcourse.web2_1_dashbunny_be.config.jwt;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	public User updateRefreshToken(User currentUser) {
		User user = userRepository.findByPhone(currentUser.getPhone())
						.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

		// Refresh Token이 만료된 경우에만 갱신
		if (user.getRefreshTokenExpiryDate().isBefore(LocalDateTime.now())) {
			user.setRefreshToken(jwtUtil.generateRefreshToken());
			user.setRefreshTokenExpiryDate(LocalDateTime.now().plusDays(7));
		}
		return userRepository.save(user);
	}

	public Optional<User> findByRefreshToken(String refreshToken) {
		return userRepository.findByRefreshToken(refreshToken);
	}

	public boolean isRefreshTokenValid(User currentUser) {
		return currentUser.getRefreshTokenExpiryDate().isAfter(LocalDateTime.now());
	}

	@Transactional
	public void deleteByUserId(User currentUser) {
		User user = userRepository.findByPhone(currentUser.getPhone())
						.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
		userRepository.deleteByRefreshToken(user.getRefreshToken());
	}

	@Transactional
	public User saveRefreshToken(User currentUser, String refreshToken, LocalDateTime expiryDate) {
		User user = userRepository.findByPhone(currentUser.getPhone())
						.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
		user.setRefreshToken(refreshToken);
		user.setRefreshTokenExpiryDate(expiryDate);
		return userRepository.save(user);
	}
}

