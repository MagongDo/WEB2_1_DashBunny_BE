package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.DiscountType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 사장님이 생성한 쿠폰의 목록을 보여주는 dto.
 */
@Getter
@AllArgsConstructor
public class OwnerCouponListResponseDto {
  private Long couponId; //쿠폰 아이디
  private String couponName; //쿠폰명
  private DiscountType discountType; //할인타입 (정률 ,정액)
  private Long discountPrice; //할인 금액
  private Long minOrderPrice; //최소 주문금액
  private LocalDateTime expiredDate; //만료기한
  private Long maximumDiscount; //최대 할인 금액 -- 필요?없으면 빼도됨
  private CouponStatus couponStatus; //상태 (활성화, 만료, 종료)

  /**
   * 엔티티에서 DTO로 데이터를 변환 위한 생성자.
   */
  public OwnerCouponListResponseDto(OwnerCoupon ownerCoupon) {
    this.couponId = ownerCoupon.getCouponId();
    this.couponName = ownerCoupon.getCouponName();
    this.discountType = ownerCoupon.getDiscountType();
    this.discountPrice = ownerCoupon.getDiscountPrice();
    this.minOrderPrice = ownerCoupon.getMinOrderPrice();
    this.expiredDate = ownerCoupon.getExpiredDate();
    this.maximumDiscount = ownerCoupon.getMaximumDiscount();
    this.couponStatus = ownerCoupon.getCouponStatus();
  }

}
