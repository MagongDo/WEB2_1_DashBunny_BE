package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;

/**
 * 선착순 쿠폰 조회 시 다양한 응답 상황을 처리.
 */
@Getter
public class FirstComeCouponResponseWrapper {
  private FirstComeCouponResponseDto coupon;
  private String message;

  /**
   * 사용자 화면에 성공적으로 쿠폰을 제공하는 경우.
   */
  public static FirstComeCouponResponseWrapper success(FirstComeCouponResponseDto coupon) {
    FirstComeCouponResponseWrapper wrapper = new FirstComeCouponResponseWrapper();
    wrapper.coupon = coupon;
    return wrapper;
  }

  /**
   * 사용자가 이미 쿠폰 이벤트에 참여한 경우.
   */
  public static FirstComeCouponResponseWrapper alreadyParticipated(String message) {
    FirstComeCouponResponseWrapper wrapper = new FirstComeCouponResponseWrapper();
    wrapper.message = message;
    return wrapper;
  }


  /**
   * 관리자가 아직 선착순 쿠폰을 활성화하지 않은 경우.
   */
  public static FirstComeCouponResponseWrapper empty() {
    return new FirstComeCouponResponseWrapper();
  }
}
