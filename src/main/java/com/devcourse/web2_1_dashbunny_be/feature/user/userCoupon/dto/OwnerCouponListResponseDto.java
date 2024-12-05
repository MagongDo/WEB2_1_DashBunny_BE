package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 화면에서 가게에서 발급한 쿠폰 목록을 보여주는 dto.
 */
@Getter
@AllArgsConstructor
public class OwnerCouponListResponseDto {
  private Long couponId; //쿠폰 아이디
  private String couponName; //쿠폰명
  private Long discountPrice; //할인 금액
  private DiscountType discountType; //할인타입 (정률 ,정액)
  private Long minOrderPrice; //최소 주문금액

  /**
   * 엔티티에서 DTO로 데이터를 변환 위한 메서드.
   */
  public OwnerCouponListResponseDto(OwnerCoupon ownerCoupon) {
    this.couponId = ownerCoupon.getCouponId();
    this.couponName = ownerCoupon.getCouponName();
    this.discountPrice = ownerCoupon.getDiscountPrice();
    this.discountType = ownerCoupon.getDiscountType();
    this.minOrderPrice = ownerCoupon.getMinOrderPrice();
  }

}
