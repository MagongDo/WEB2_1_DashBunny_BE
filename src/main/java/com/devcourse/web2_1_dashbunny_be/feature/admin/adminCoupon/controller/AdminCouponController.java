package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.controller;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.*;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service.AdminCouponService;
import java.util.List;

import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 관리자 쿠폰 생성 controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupon")
public class AdminCouponController {
  private final AdminCouponService adminCouponService;
  private final UserService userService;

  /**
   * 관리자 쿠폰 등록 api (POST).
   */
  @PostMapping
  public ResponseEntity<AdminCoupon> addAdminCoupon(@RequestBody AddAdminCouponRequestDto request,
                                                    @RequestHeader("Authorization") String authorizationHeader) {
    String phone = userService.getCurrentUser(authorizationHeader).getPhone();
    AdminCoupon adminCoupon = adminCouponService.saveAdminCoupon(request,phone);
    return ResponseEntity.status(HttpStatus.CREATED).body(adminCoupon);
  }

//  /**
//   * 관리자 쿠폰 목록 조회 api (GET).
//   */
//  @GetMapping
//  public ResponseEntity<List<AdminCouponListResponseDto1>> getAllAdminCoupon() {
//    List<AdminCouponListResponseDto1> adminCoupons = adminCouponService.findAllAdminCoupons();
//    return ResponseEntity.status(HttpStatus.OK).body(adminCoupons);
//  }

  /**
   * 만료 쿠폰을 제외한 관리자 쿠폰 목록 조회 api (GET).
   */
  @GetMapping
  public ResponseEntity<List<AdminCouponListResponseDto1>> getAvailableCoupons(
          @RequestHeader("Authorization") String authorizationHeader
  ) {
    String phone = userService.getCurrentUser(authorizationHeader).getPhone();
    List<AdminCouponListResponseDto1> adminCoupons = adminCouponService.findAvailableCoupons();
    return ResponseEntity.status(HttpStatus.OK).body(adminCoupons);
  }

//  /**
//   * 단일 관리자 쿠폰 조회 api (GET).
//   */
//  @GetMapping("/{couponId}")
//  public ResponseEntity<AdminCouponResponseDto> getAdminCoupon(@PathVariable Long couponId) {
//    AdminCouponResponseDto adminCouponRequestDto = adminCouponService.finAdminCouponById(couponId);
//    return ResponseEntity.status(HttpStatus.OK).body(adminCouponRequestDto);
//  }

  /**
   * 관리자 쿠폰 상태 변경 api (PUT).
   */
  @PutMapping("/{couponId}")
  public ResponseEntity<AdminCoupon> updateAdminCouponStatus(@PathVariable Long couponId, @RequestBody ChangeAdminCouponStatusRequestDto request
  ,@RequestHeader("Authorization") String authorizationHeader) {
    String phone = userService.getCurrentUser(authorizationHeader).getPhone();
    AdminCoupon coupon = adminCouponService.finAdminCouponStatusChange(couponId, request);
    return ResponseEntity.status(HttpStatus.OK).body(coupon);
  }



}
