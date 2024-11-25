package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon.AddOwnerCouponRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon.OwnerCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository.OwnerCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



/**
 * 사장님 쿠폰 service.
 */
@Service
@RequiredArgsConstructor
public class OwnerCouponService {
  private final OwnerCouponRepository ownerCouponRepository;

  /**
   *  사장님 쿠폰 생성.
   */
  public OwnerCoupon saveOwnerCoupon(AddOwnerCouponRequestDto request) {
    return ownerCouponRepository.save(request.toEntity());
  }


  /**
   * 사장님 쿠폰 목록 조회.
   */
  public List<OwnerCouponListResponseDto> findAllOwnerCoupons() {
    List<OwnerCoupon> ownerCoupons = ownerCouponRepository.findAll();
    return ownerCoupons.stream()
            .map(OwnerCouponListResponseDto::new)
            .toList();

  }

  //이후는 호정님께 토스하겠습니다!!
  //쿠폰 만료기한이 넘으면 CouponStatus가 EXPIRED가 된다는것을 생각해주십쇼!


}
