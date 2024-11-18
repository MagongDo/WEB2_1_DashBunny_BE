package com.devcourse.web2_1_dashbunny_be.domain.owner.role;

public enum StoreStatus {
        PENDING,    // 등록 대기 중
        REGISTERED,  // 등록 완료
        REGISTRATION, // 가게 등록 실패
        OPEN, // 영업중
        PENDING_OPEN, // 영업 준비 중
        TEMP_CLOSE, // 휴업중
        CLOSURE_PENDING, // 페업 신청 대기 중
        CLOSED // 폐업
}
