package com.devcourse.web2_1_dashbunny_be.domain.user.role;

public enum OrderStatus {
    PENDING,           // 주문 대기 중 (접수 대기)
    IN_PROGRESS,       // 조리 중
    DECLINED,          // 주문 거절
    CANCEL,
    OUT_FOR_DELIVERY,  // 배달 중
    DELIVERED          // 배달 완료
}
