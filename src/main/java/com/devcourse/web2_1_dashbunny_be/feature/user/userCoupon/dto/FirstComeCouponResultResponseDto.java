package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자가 발급한 선착순 쿠폰 다운로드 후 화면에 보여줄 데이터 dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FirstComeCouponResultResponseDto {
  private Long couponId; //쿠폰 아이디
  private Long discountPrice; //할인금액
  private DiscountType discountType; //할인 타입 (정률,정액)

}
