package com.devcourse.web2_1_dashbunny_be.domain.user.role;

public enum DeliveryRequestStatus { // 픽업
	REQUESTED, // 배달 요청 상태
	ASSIGNED, // 배달 접수
	DELIVERED, // 배달 완료
	CANCELED // 취소
}
