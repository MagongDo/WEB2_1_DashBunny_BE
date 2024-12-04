package com.devcourse.web2_1_dashbunny_be.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.access.token.expiration.ms}")
	private long jwtExpirationInMs;

	@Value("${jwt.refresh.token.expiration.ms}")
	private long refreshTokenExpirationInMs;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	// Access Token 생성
	public String generateAccessToken(String username, String role) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder()
						.setSubject(username)
						.claim("role", role)
						.setIssuedAt(now)
						.setExpiration(expiryDate)
						.signWith(key, SignatureAlgorithm.HS256)
						.compact();
	}

	// Refresh Token 생성
	public String generateRefreshToken() {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

		return Jwts.builder()
						.setIssuedAt(now)
						.setExpiration(expiryDate)
						.signWith(key, SignatureAlgorithm.HS256)
						.compact();
	}

	// JWT 에서 사용자 번호 추출
	public String getUserPhoneFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(token)
						.getBody();

		return claims.getSubject();
	}

	// JWT 유효성 검증
	public boolean validateToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
			return true;
		} catch (JwtException ex) {
			log.error("유효성 검증 실패 : {}",ex.getMessage());
			return false;
		}
	}

	// Authorization 헤더에서 토큰 추출
	public String extractTokenFromHeader(String header) {
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7); // "Bearer " 이후의 부분을 반환
		}
		throw new IllegalArgumentException("유효하지 않은 Authorization 헤더입니다.");
	}
}

