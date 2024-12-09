package com.devcourse.web2_1_dashbunny_be.feature.delivery.service;

import com.devcourse.web2_1_dashbunny_be.feature.delivery.controller.SseController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SseService  {

	private final SseController sseController;


	/**
	 * 배차 알림을 특정 배달원에게 전송
	 * @param userId 배달원 ID
	 * @param data 전송할 데이터
	 */
	public void notifyDeliveryAssignment(Long userId, Object data) {
		sseController.sendAssignmentNotification(userId, data);
	}

}
