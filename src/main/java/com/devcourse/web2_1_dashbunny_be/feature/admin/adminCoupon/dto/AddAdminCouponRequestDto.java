package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.DiscountType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 새로운 쿠폰을 생성 정보를 받기 위한 dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddAdminCouponRequestDto {

  private String couponName; //쿠폰명
  private CouponType couponType; //쿠폰타입(일반,선착순)
  private Long discountPrice; //할인금액
  private DiscountType discountType; //할인타입 (정률 ,정액)
  private Long minOrderPrice; //최소 주문금액
  private Long maximumDiscount; //최대 할인 금액
  private Long maxIssuance; //발급한도
  private LocalDateTime expiredDate; //만료기한
  private String couponDescription; //쿠폰 설명
  private LocalDateTime downloadStartDate; //쿠폰 오픈일(선착순 쿠폰일경우)

  /**
   * AdminCoupon 객체 생성 메서드.
   */
  public AdminCoupon toEntity() {
    return AdminCoupon.builder()
            .couponName(couponName)
            .couponType(couponType)
            .discountPrice(discountPrice)
            .discountType(discountType)
            .minOrderPrice(minOrderPrice)
            .maximumDiscount(maximumDiscount)
            .maxIssuance(maxIssuance)
            .expiredDate(expiredDate)
            .couponDescription(couponDescription)
            .downloadStartDate(downloadStartDate)
            .couponStatus(CouponStatus.PENDING) //쿠폰상태: PENDING (대기)
            .build();

  }


}
