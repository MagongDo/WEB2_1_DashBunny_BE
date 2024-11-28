package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import java.time.LocalDateTime;

import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * 새로운 쿠폰을 생성 정보를 받기 위한 dto.
 */
@Slf4j
@Getter
@Setter
@Builder
public class CreateOwnerCouponRequestDto {

  private String couponName; //쿠폰명
  private Long discountPrice; //할인금액
  private DiscountType discountType; //할인타입 (정률 ,정액)
  private Long minOrderPrice; //최소 주문금액
  private Long maximumDiscount; //최대 할인 금액
  private int expiredDay; //만료기한
  private String couponDescription; //쿠폰 설명

  /**
   * OwnerCoupon dto -> entity 변환.
   */
  public OwnerCoupon toEntity(StoreManagement store) {
    log.info("Creating OwnerCoupon{}", store);
    OwnerCoupon ownerCoupon = new OwnerCoupon();
    ownerCoupon.setStoreManagement(store);
    ownerCoupon.setCouponName(couponName);
    ownerCoupon.setDiscountPrice(discountPrice);
    ownerCoupon.setDiscountType(discountType);
    ownerCoupon.setMinOrderPrice(minOrderPrice);
    ownerCoupon.setCouponStatus(CouponStatus.ACTIVE);
    ownerCoupon.setExpiredDate(LocalDateTime.now());
    ownerCoupon.setCreatCouponAt(LocalDateTime.now());
    ownerCoupon.setCouponDescription(couponDescription);
    ownerCoupon.setMaximumDiscount(maximumDiscount);
    return ownerCoupon;
  }


}
