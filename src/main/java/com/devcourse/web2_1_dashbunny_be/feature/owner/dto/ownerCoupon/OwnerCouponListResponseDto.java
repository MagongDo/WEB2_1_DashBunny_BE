package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


/**
 * 사장님이 생성한 쿠폰의 목록을 보여주는 dto.
 */
@Getter
@Builder
public class OwnerCouponListResponseDto {
  private Long couponId; //쿠폰 아이디
  private String couponName; //쿠폰명
  private DiscountType discountType; //할인타입 (정률 ,정액)
  private Long discountPrice; //할인 금액
  private Long minOrderPrice; //최소 주문금액
  private Long maximumDiscount; //최대 할인 금액 -- 필요?없으면 빼도됨
  private CouponStatus couponStatus; //상태 (활성화, 만료, 종료)
  private int expiredDay;

  /**
   * 엔티티에서 DTO로 데이터를 변환 위한 메서드.
   */
  public static OwnerCouponListResponseDto fromEntity(OwnerCoupon ownerCoupon) {
    int remainingDays = (int) ChronoUnit.DAYS
            .between(LocalDateTime.now(), ownerCoupon.getExpiredDate());

    return OwnerCouponListResponseDto.builder()
            .couponId(ownerCoupon.getCouponId())
            .couponName(ownerCoupon.getCouponName())
            .discountType(ownerCoupon.getDiscountType())
            .discountPrice(ownerCoupon.getDiscountPrice())
            .minOrderPrice(ownerCoupon.getMinOrderPrice())
            .maximumDiscount(ownerCoupon.getMaximumDiscount())
            .couponStatus(ownerCoupon.getCouponStatus())
            .expiredDay(remainingDays)
            .build();
  }

}

//만료일 - 생성일 한 day 담아서 전달하기