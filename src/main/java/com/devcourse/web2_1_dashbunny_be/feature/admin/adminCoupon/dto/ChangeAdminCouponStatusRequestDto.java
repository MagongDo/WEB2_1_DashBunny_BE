package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 쿠폰 상태 변경 정보를 담는 dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAdminCouponStatusRequestDto {
  private CouponStatus couponStatus; // 사용가능, 조기 종료, 만료
}
