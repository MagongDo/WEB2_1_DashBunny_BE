package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.DiscountType;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

/**
 * 새로운 쿠폰을 생성 정보를 받기 위한 dto.
 */
@Getter
@Setter
@Builder
public class CreateOwnerCouponRequestDto {

  private String couponName; //쿠폰명
  private Long discountPrice; //할인금액
  private DiscountType discountType; //할인타입 (정률 ,정액)
  private Long minOrderPrice; //최소 주문금액
  private Long maximumDiscount; //최대 할인 금액
  private int expiredDate; //만료기한
  private String couponDescription; //쿠폰 설명

  /**
   * OwnerCoupon dto -> entity 변환.
   */
  public OwnerCoupon toEntity() {
    OwnerCoupon ownerCoupon = new OwnerCoupon();
    ownerCoupon.setCouponName(couponName);
    ownerCoupon.setDiscountPrice(discountPrice);
    ownerCoupon.setDiscountType(discountType);
    ownerCoupon.setMinOrderPrice(minOrderPrice);
    ownerCoupon.setExpiredDate(LocalDateTime.now());
    ownerCoupon.setMaximumDiscount(maximumDiscount);
    return ownerCoupon;
  }


}
