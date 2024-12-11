package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon.OwnerCouponListResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 쿠폰 관리 interface.
 */
@Repository
public interface OwnerCouponService {

  /**
  * 쿠폰 전체 조회를 위한 api service.
  */
  List<OwnerCouponListResponseDto> getCounponList(String storeId);

  /**
  * 쿠폰 상태 업데이트를 위한 api service.
  */
  void updateCouponStatus(Long couponId);

  /**
  * 새로운 쿠폰 저장을 위한 api service.
  */
  void saveOwnerCoupon(OwnerCoupon ownerCoupon, int day);


}
