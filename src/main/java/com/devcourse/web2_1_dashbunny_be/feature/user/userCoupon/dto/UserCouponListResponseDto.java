package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 사용자 쿠폰함 쿠폰 목록 조회 Dto.
 */
@Getter
@AllArgsConstructor
public class UserCouponListResponseDto {
    private String userCouponId; //사용자 쿠폰 아이디
    private String couponName; //쿠폰명
    private Long discountPrice; //할인금액
    private DiscountType discountType; //할인 타입 (정률,정액)
    private Long minOrderPrice; //최소 주문 금액
    private Long maximumDiscount; //최대 할인 금액 (정률 방식에만)
    private LocalDateTime expiredDate; //쿠폰 만료기한
    private String couponDescription; //쿠폰 상세 내용



}
