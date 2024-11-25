package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AddAdminCouponRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.ChangeAdminCouponStatusRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository.AdminCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 관리자 쿠폰생성 service.
 */
@Service
@RequiredArgsConstructor
public class AdminCouponService {
  private final AdminCouponRepository adminCouponRepository;

  /**
   * 쿠폰 생성.
   */
  public AdminCoupon saveAdminCoupon(AddAdminCouponRequestDto request) {
    return adminCouponRepository.save(request.toEntity());
  }


  /**
   * 쿠폰 목록 조회.
   */
  public List<AdminCouponListResponseDto> findAllAdminCoupons() {
    List<AdminCoupon> adminCoupons = adminCouponRepository.findAll();
    return adminCoupons.stream()
            .map(AdminCouponListResponseDto::new)
            .toList();
  }


  /**
   * 쿠폰 단일 조회.
   */
  public AdminCouponResponseDto finAdminCouponById(Long couponId) {
    AdminCoupon coupon = adminCouponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("not found couponId: " + couponId));
    return new AdminCouponResponseDto(coupon);
  }

  /**
   * 쿠폰 상태 변경.
   */
  public AdminCoupon finAdminCouponStatusChange(Long couponId, ChangeAdminCouponStatusRequestDto status) {
    AdminCoupon coupon = adminCouponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("not found couponId: " + couponId));
    coupon.setCouponStatus(status.getCouponStatus());
    return adminCouponRepository.save(coupon);
  }
}
