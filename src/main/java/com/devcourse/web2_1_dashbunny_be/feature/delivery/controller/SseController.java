package com.devcourse.web2_1_dashbunny_be.feature.delivery.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.service.DeliveryService;
import com.devcourse.web2_1_dashbunny_be.feature.delivery.service.SseService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RestController
@RequestMapping("/api/delivery/worker/sse")
@RequiredArgsConstructor
public class SseController {

	// userId와 SseEmitter를 매핑하는 ConcurrentHashMap
	private final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final UserService userService;
	private final DeliveryService deliveryService;

	/**
	 * SSE 구독 엔드포인트
	 * @param token 배달원 JWT
	 * @return SseEmitter 객체
	 */
	@GetMapping(path = "/assignments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribeToAssignments(@RequestParam String token) {
		log.info("subscribeToAssignments token : {}",token);
		User deliveryWorker = userService.getCurrentUser(token);
		log.info("subscribeToAssignments user : {}", deliveryWorker);
		Long userId = deliveryWorker.getUserId();
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // 타임아웃 없음
		emitters.put(userId, emitter);

		emitter.onCompletion(() -> emitters.remove(userId));
		emitter.onTimeout(() -> emitters.remove(userId));
		emitter.onError((e) -> emitters.remove(userId));

		return emitter;
	}

	/**
	 * 특정 배달원에게 배차 알림 전송
	 * @param userId 배달원 ID
	 * @param data 전송할 데이터
	 */
	public void sendAssignmentNotification(Long userId, Object data) {
		SseEmitter emitter = emitters.get(userId);
		log.info("sendAssignmentNotification userId: {}, emitter: {}, data: {}", userId, emitter, data);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
								.name("assignment")
								.data(data));
				log.info("SSE 전송 성공: userId={}, data={}", userId, data);
			} catch (IOException e) {
				log.error("SSE 전송 실패: userId={}, error={}", userId, e.getMessage());
				emitters.remove(userId);
			}
		} else {
			log.warn("해당 userId={} 에 대한 emitter가 없습니다.", userId);
		}
	}

	/**
	 * JWT 값을 헤더로 받아 SSE 연결을 처리하는 메서드
	 *
	 * @param authorizationHeader 클라이언트에서 전달한 JWT 값
	 * @return SSE 연결 URL
	 */
	@GetMapping("/start")
	public ResponseEntity<String> startSseConnection(@RequestHeader("Authorization") String authorizationHeader) {
		// 클라이언트로 SSE URL 반환 (헤더에 포함된 JWT 값 활용)
		String sseUrl = "/api/delivery/worker/sse/assignments?token=" + authorizationHeader;
		deliveryService.statusChangeNotReadyDeliveryWorker(authorizationHeader);
		return ResponseEntity.ok(sseUrl);
	}

}
