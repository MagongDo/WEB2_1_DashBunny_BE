package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.controller;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AddAdminCouponRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.ChangeAdminCouponStatusRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service.AdminCouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 관리자 쿠폰 생성 controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminCoupon")
public class AdminCouponController {
  private final AdminCouponService adminCouponService;

  /**
   * 관리자 쿠폰 등록 api (POST).
   */
  @PostMapping
  public ResponseEntity<AdminCoupon> addAdminCoupon(@RequestBody AddAdminCouponRequestDto request) {
    AdminCoupon adminCoupon = adminCouponService.saveAdminCoupon(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(adminCoupon);
  }

  /**
   * 관리자 쿠폰 목록 조회 api (GET)
   */
  @GetMapping
  public ResponseEntity<List<AdminCouponListResponseDto>> getAllAdminCoupon() {
    List<AdminCouponListResponseDto> adminCoupons = adminCouponService.findAllAdminCoupons();
    return ResponseEntity.status(HttpStatus.OK).body(adminCoupons);
  }

  /**
   * 단일 관리자 쿠폰 조회 api (GET).
   */
  @GetMapping("/{couponId}")
  public ResponseEntity<AdminCouponResponseDto> getAdminCoupon(@PathVariable String couponId) {
    AdminCouponResponseDto adminCouponRequestDto = adminCouponService.finAdminCouponById(couponId);
    return ResponseEntity.status(HttpStatus.OK).body(adminCouponRequestDto);
  }

  /**
   * 관리자 쿠폰 상태 변경 api (PUT).
   */
  @PutMapping("/{couponId}")
  public ResponseEntity<AdminCoupon> updateAdminCouponStatus(@PathVariable String couponId, @RequestBody ChangeAdminCouponStatusRequestDto request) {
    AdminCoupon coupon = adminCouponService.finAdminCouponStatusChange(couponId, request);
    return ResponseEntity.status(HttpStatus.OK).body(coupon);
  }

}
