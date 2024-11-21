package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
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
public class AdminCouponAddRequestDto {

  private String couponName;
  private CouponType couponType;
  private CouponStatus couponStatus;
  private Long discountPrice;
  private Long minOrderPrice;
  private Long maxIssuance;
  private LocalDateTime expiredDate;
  private String couponDescription;

  /**
   * AdminCoupon 객체 생성 메서드.
   */
  public AdminCoupon toEntity() {
    return AdminCoupon.builder()
            .couponName(couponName)
            .couponType(couponType)
            .discountPrice(discountPrice)
            .minOrderPrice(minOrderPrice)
            .couponStatus(couponStatus)
            .couponType(couponType)
            .maxIssuance(maxIssuance)
            .expiredDate(expiredDate)
            .couponDescription(couponDescription)
            .build();

  }


}
