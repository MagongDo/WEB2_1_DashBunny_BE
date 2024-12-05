package com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart;

import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니에서 선택된 사용자 쿠폰 ID를 넘겨주는 Dto.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsersCheckCouponDto {
  private String userCouponId; //사용자 쿠폰 아이디
  private Long discountPrice; //할인금액
  private DiscountType discountType; //할인 타입 (정률,정액)
  private Long minOrderPrice; //최소 주문 금액
  private Long maximumDiscount; //최대 할인 금액 (정률 방식에만)
}
