package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



/**
 * 관리자가 발급한 일반 쿠폰 목록 조회 dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralCouponListResponseDto {
  private Long couponId; //쿠폰 아이디
  private String couponName; //쿠폰명
  private Long discountPrice; //할인금액
  private DiscountType discountType; //할인 타입 (정률,정액)
  private Long minOrderPrice; //최소 주문 금액
  private Long maximumDiscount; //최대 할인 금액 (정률 방식에만)
  private LocalDateTime expiredDate; //쿠폰 만료기한


  /**
   * AdminCoupon엔티티에서 DTO로 데이터를 변환을 위한 생성자.
   */
  public GeneralCouponListResponseDto(AdminCoupon adminCoupon) {
    this.couponId = adminCoupon.getCouponId();
    this.couponName = adminCoupon.getCouponName();
    this.discountPrice = adminCoupon.getDiscountPrice();
    this.discountType = adminCoupon.getDiscountType();
    this.minOrderPrice = adminCoupon.getMinOrderPrice();
    this.maximumDiscount = adminCoupon.getMaximumDiscount();
    this.expiredDate = adminCoupon.getExpiredDate();
  }

}
