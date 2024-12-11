package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 관리자가 발급한 선착순 쿠폰 조회 dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FirstComeCouponResponseDto {
  private Long couponId; //쿠폰 아이디
  private String couponName; //쿠폰명
  private Long discountPrice; //할인 금액
  private DiscountType discountType; //할인 유형
  private Long maximumDiscount; //최대 할인 금액 (정률 방식에만)
  private LocalDateTime downloadStartDate; // (선착순 쿠폰일 경우)다운로드 시작 시간


  /**
   * AdminCoupon 엔티티를 받아 초기화하는 생성자.
   */
  public FirstComeCouponResponseDto(AdminCoupon adminCoupon) {
    this.couponId = adminCoupon.getCouponId();
    this.couponName = adminCoupon.getCouponName();
    this.discountPrice = adminCoupon.getDiscountPrice();
    this.discountType = adminCoupon.getDiscountType();
    this.maximumDiscount = adminCoupon.getMaximumDiscount();
    this.downloadStartDate = adminCoupon.getDownloadStartDate();
  }
}
