package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponAddRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponListRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponStatusChangeRequestDto;
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
  public AdminCoupon saveAdminCoupon(AdminCouponAddRequestDto request) {
    return adminCouponRepository.save(request.toEntity());
  }


  /**
   * 쿠폰 목록 조회.
   */
  public List<AdminCouponListRequestDto> findAllAdminCoupons() {
    List<AdminCoupon> adminCoupons = adminCouponRepository.findAll();
    return adminCoupons.stream()
            .map(AdminCouponListRequestDto::new)
            .toList();
  }


  /**
   * 쿠폰 단일 조회.
   */
  public AdminCouponRequestDto finAdminCouponById(String couponId) {
    AdminCoupon coupon = adminCouponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("not found couponId: " + couponId));
    return new AdminCouponRequestDto(coupon);
  }

  /**
   * 쿠폰 상태 변경.
   */
  public AdminCoupon finAdminCouponStatusChange(String couponId, AdminCouponStatusChangeRequestDto status) {
    AdminCoupon coupon = adminCouponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("not found couponId: " + couponId));
    coupon.setCouponStatus(status.getCouponStatus());
    return adminCouponRepository.save(coupon);
  }
}
