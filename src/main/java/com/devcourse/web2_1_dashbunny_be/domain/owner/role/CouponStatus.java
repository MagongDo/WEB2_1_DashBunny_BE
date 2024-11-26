package com.devcourse.web2_1_dashbunny_be.domain.owner.role;

/**
 * 사장님 쿠폰 상태를 나타내는 enum.
 */
public enum CouponStatus {
    //PENDING, //대기
    ACTIVE, // 사용가능
    EARLY_TERMINATED, //조기종료
    EXPIRED //만료
}
