package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.*;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository.AdminCouponRepository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
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


//  /**
//   * 쿠폰 목록 조회.
//   */
//  public List<AdminCouponListResponseDto1> findAllAdminCoupons() {
//    List<AdminCoupon> adminCoupons = adminCouponRepository.findAll();
//    return adminCoupons.stream()
//            .map(AdminCouponListResponseDto1::new)
//            .toList();
//  }

  /**
   * 만료 쿠폰을 제외한 쿠폰 목록 조회.
   */
  public List<AdminCouponListResponseDto1>  findAvailableCoupons() {
    List<AdminCoupon> adminCoupons = adminCouponRepository.findByCouponStatusNot(CouponStatus.EXPIRED); //couponStatus가 말료가 아닌 상태의 목록만 조회
    return adminCoupons.stream()
            .map(AdminCouponListResponseDto1::new)
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

  /**
   * 매일 자정마다 쿠폰의 만료기한 확인후 만료기한이 지난 쿠폰은 쿠폰 상태를 EXPIRED로 변환.
   */
  @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
  public void updateExpiredCoupons() {
    List<AdminCoupon> expiredCoupons = adminCouponRepository.findByExpiredDateBeforeAndCouponStatusNot(
            LocalDateTime.now(), CouponStatus.EXPIRED);

    for (AdminCoupon coupon : expiredCoupons) {
      coupon.setCouponStatus(CouponStatus.EXPIRED);
    }

    adminCouponRepository.saveAll(expiredCoupons);
  }
}
