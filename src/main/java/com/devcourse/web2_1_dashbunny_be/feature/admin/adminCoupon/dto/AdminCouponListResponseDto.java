package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 관리자가 생성한 쿠폰의 목록을 보여주는 dto.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCouponListResponseDto {
  private String couponId;
  private String couponName;
  private CouponType couponType;
  //private String couponDescription;
  private Long discountPrice; //할인 금액
  //private Long minOrderPrice; //최소 주문 금액
  private LocalDateTime expiredDate; //쿠폰 만료기한
  private CouponStatus couponStatus; //쿠폰 상태

  /**
   * AdminCoupon엔티티에서 DTO로 데이터를 변환을 위한 생성자.
   */
  public AdminCouponListResponseDto(AdminCoupon adminCoupon) {
    this.couponId = adminCoupon.getCouponId();
    this.couponName = adminCoupon.getCouponName();
    this.couponType = adminCoupon.getCouponType();
    //this.couponDescription = adminCoupon.getCouponDescription();
    this.discountPrice = adminCoupon.getDiscountPrice();
    //this.minOrderPrice = adminCoupon.getMinOrderPrice();
    this.expiredDate = adminCoupon.getExpiredDate();
    this.couponStatus = adminCoupon.getCouponStatus();
  }

}
