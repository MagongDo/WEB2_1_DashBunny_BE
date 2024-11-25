package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon.OwnerCouponListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 쿠폰 서비스 로직 구현 class.
 */
@Service
@RequiredArgsConstructor
public class OwnerCouponServiceImpl implements OwnerCouponService {

  private final Validator validator;

  @Override
  public List<OwnerCouponListResponseDto> getCounponList(String storeId) {
    StoreManagement store = validator.validateStoreId(storeId);
    store.
    return List.of();
  }

  @Override
  public void updateCouponStatus(Long couponId) {

  }

  @Override
  public void saveOwnerCoupon(OwnerCoupon ownerCoupon) {

  }
}
