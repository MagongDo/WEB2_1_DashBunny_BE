package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon.OwnerCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository.OwnerCouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 쿠폰 서비스 로직 구현 class.
 */
@Service
@RequiredArgsConstructor
public class OwnerCouponServiceImpl implements OwnerCouponService {

  private final Validator validator;
  private final OwnerCouponRepository ownerCouponRepository;

  @Override
  public List<OwnerCouponListResponseDto> getCounponList(String storeId) {
    StoreManagement store = validator.validateStoreId(storeId);
    List<OwnerCouponListResponseDto> response = store.getCouponList().stream()
           .map(OwnerCouponListResponseDto::fromEntity).toList();
    return response;
  }

  @Override
  public void updateCouponStatus(Long couponId) {
    OwnerCoupon coupon = validator.validateCouponId(couponId);
    coupon.setCouponStatus(CouponStatus.EARLY_TERMINATED);
    ownerCouponRepository.save(coupon);
  }

  //-1 시키키
  //반환 할때도 테스트 해보기
  @Override
  public void saveOwnerCoupon(OwnerCoupon ownerCoupon, int day) {
    LocalDateTime time = ownerCoupon.getExpiredDate();
    LocalDateTime expireDate = time.plusDays(day);
    ownerCoupon.setExpiredDate(expireDate);
    ownerCouponRepository.save(ownerCoupon);
  }
  //검증이 지금 가게에 대한 부분을 컨트롤러에서 하고 있다. 이걸 서비스단으로 옮기는 작업을 해야한다.
}
