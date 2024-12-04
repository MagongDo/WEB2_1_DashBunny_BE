package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 관리자가 생성한 쿠폰의 목록을 보여주는 dto.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCouponListResponseDto1 {
  private Long couponId;
  private String couponName;
  private CouponType couponType;
  private String couponDescription;
  private Long discountPrice; //할인 금액
  private DiscountType discountType; //할인 타입 (정률,정액)
  private Long minOrderPrice; //최소 주문 금액
  private Long maximumDiscount; //최대 할인 금액 (정률 방식에만)
  private LocalDateTime expiredDate; //쿠폰 만료기한
  private CouponStatus couponStatus; //쿠폰 상태
  private Long maxIssuance; //발급한도 (선착순 쿠폰일 경우)
  private LocalDateTime downloadStartDate; //쿠폰 오픈일(선착순 쿠폰일경우)

  /**
   * AdminCoupon엔티티에서 DTO로 데이터를 변환을 위한 생성자.
   */
  public AdminCouponListResponseDto1(AdminCoupon adminCoupon) {
    this.couponId = adminCoupon.getCouponId();
    this.couponName = adminCoupon.getCouponName();
    this.couponType = adminCoupon.getCouponType();
    this.couponDescription = adminCoupon.getCouponDescription();
    this.discountPrice = adminCoupon.getDiscountPrice();
    this.discountType = adminCoupon.getDiscountType();
    this.minOrderPrice = adminCoupon.getMinOrderPrice();
    this.expiredDate = adminCoupon.getExpiredDate();
    this.couponStatus = adminCoupon.getCouponStatus();
    this.maxIssuance = adminCoupon.getMaxIssuance();
    this.downloadStartDate = adminCoupon.getDownloadStartDate();
  }

}
