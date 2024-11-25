package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon;

import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사장님 쿠폰 상태 변경 정보를 담는 dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOwnerCouponRequestDto {
  private CouponStatus couponStatus;
}
