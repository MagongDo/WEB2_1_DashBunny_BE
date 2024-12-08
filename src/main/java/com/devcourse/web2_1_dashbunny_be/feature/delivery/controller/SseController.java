package com.devcourse.web2_1_dashbunny_be.feature.delivery.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

	/**
	 * SSE 구독 엔드포인트
	 * @param authorizationHeader 배달원 JWT
	 * @return SseEmitter 객체
	 */
	@GetMapping("/sse/assignments")
	public SseEmitter subscribeToAssignments(@RequestHeader("Authorization") String authorizationHeader) {
		User deliveryWorker = userService.getCurrentUser(authorizationHeader);
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
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
								.name("assignment")
								.data(data));
			} catch (IOException e) {
				emitters.remove(userId);
			}
		}
	}

}
