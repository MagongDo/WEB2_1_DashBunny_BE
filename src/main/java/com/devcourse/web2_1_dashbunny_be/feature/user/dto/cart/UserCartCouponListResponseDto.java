package com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart;

import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 장바구니에서 사용가능한 쿠폰을 보여주는 Dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCartCouponListResponseDto {
  private String userCouponId; //사용자 쿠폰 아이디
  private String couponName; //쿠폰명
  private Long discountPrice; //할인금액
  private DiscountType discountType; //할인 타입 (정률,정액)
  private Long minOrderPrice; //최소 주문 금액
  private Long maximumDiscount; //최대 할인 금액 (정률 방식에만)
  private LocalDateTime expiredDate; //쿠폰 만료기한
  private String storeName; //가게 이름



}
