package com.devcourse.web2_1_dashbunny_be.config.jwt;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.TokenRefreshRequest;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.TokenRefreshResponse;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class JWTController {
	
	/* Headers 사용법 ("" < 제외 후 모든 토큰 )
	* Authorization  |  "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdW..."
	*/

	// Spring Security AuthenticationManager: 사용자 인증 처리
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final RefreshTokenService refreshTokenService;
	private final UserRepository userRepository;

	/**
	 * Refresh Token을 사용해 새로운 Access Token 발급
	 * @param request 클라이언트에서 보낸 Refresh Token 요청
	 * @return 새로운 Access Token과 Refresh Token
	 */
	@PostMapping("/refresh")
	public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
		// 클라이언트로부터 받은 Refresh Token
		String requestRefreshToken = request.getRefreshToken();

		// RefreshToken DB 존재 유무 확인
		User refreshToken = userRepository.findByRefreshToken(requestRefreshToken)
						.orElseThrow(() -> new RuntimeException("RefreshToken 없음"));

		// RefreshToken 유효한지 확인
		if (!refreshTokenService.isRefreshTokenValid(refreshToken)) {
			throw new RuntimeException("RefreshToken 만료");
		}

		// RefreshToken 연결된 사용자 조회
		User user = userRepository.findById(refreshToken.getUserId())
						.orElseThrow(() -> new RuntimeException("사용자 없음")); // 사용자 정보 없으면 예외 발생

		// 새로운 Access Token 생성
		String newAccessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());

		// 새로운 Refresh Token 생성
		String newRefreshTokenStr = jwtUtil.generateRefreshToken();

		user.setRefreshToken(newRefreshTokenStr);
		user.setRefreshTokenExpiryDate(java.time.LocalDateTime.now().plusSeconds(604800));

		// 기존 Refresh Token 삭제 및 새로 저장
		refreshTokenService.deleteByUserId(user);
		userRepository.save(user);

		// 새 AccessToken과 RefreshToken을 반환
		return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, newRefreshTokenStr));
	}

	/**
	 * Access Token 을 이용해 User 객체 반환
	 * @param authorizationHeader 클라이언트에서 보낸 Access Token 요청
	 * @return User 객체 반환
	 */
	@GetMapping("/getCurrentUser-authorization")
	public ResponseEntity<User> securedEndpoint(@RequestHeader("Authorization") String authorizationHeader) {
		User currentUser = userService.getCurrentUser(authorizationHeader);
		return ResponseEntity.ok(currentUser);
	}

}
