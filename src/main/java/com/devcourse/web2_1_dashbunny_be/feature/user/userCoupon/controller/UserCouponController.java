package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service.AdminCouponService;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service.OwnerCouponService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.CustomUserDetailsService;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.FirstComeCouponResponseWrapper;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.GeneralCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.OwnerCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.UserCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 사용자 쿠폰 컨트롤러.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/coupon")
public class UserCouponController {
  private final UserCouponService userCouponService;
  private final AdminCouponService adminCouponService;
  private final OwnerCouponService ownerCouponService;
  private final CustomUserDetailsService customUserDetailsService;

  /**
   * 관리자 발급한 일반 쿠폰 목록 조회 api (GET).
   */
  @GetMapping("/general")
  public ResponseEntity<List<GeneralCouponListResponseDto>> getGeneralCoupon() {
    List<GeneralCouponListResponseDto> coupons = userCouponService.findActiveRegularCoupons();
    return ResponseEntity.status(HttpStatus.OK).body(coupons);
  }

  /**
   * 관리자 발급한 선착순 쿠폰  조회 api (GET).
   */
  @GetMapping("/first-come")
  public ResponseEntity<?> getFirstComeCoupon() {
    FirstComeCouponResponseWrapper response = userCouponService.findActiveFirstComeCoupon();

    if (response.getCoupon() != null) {
      return ResponseEntity.ok(response.getCoupon());
    } else if (response.getMessage() != null) {
      return ResponseEntity.ok(Collections.singletonMap("message", response.getMessage()));
    } else {
      return ResponseEntity.noContent().build(); // 쿠폰이 활성화되지 않은 경우
    }
  }

  /**
   * 사장님이 발급한 쿠폰 목록 조회 api (GET).
   */
  @GetMapping("/owner/{storeId}")
  public ResponseEntity<List<OwnerCouponListResponseDto>> getOwnerCoupon(@PathVariable String storeId) {
    List<OwnerCouponListResponseDto> ownerCoupons = userCouponService.findActiveOwnerCoupons(storeId);
    return ResponseEntity.status(HttpStatus.OK).body(ownerCoupons);
  }

  /**
   * 일반 쿠폰 다운로드 api (POST).
   */
  @PostMapping("/download/general/{couponId}")
  public ResponseEntity<?> downloadGeneralCoupon(@PathVariable Long couponId) {
    //현재 사용자의 userId를 가져와야함
    userCouponService.downloadCoupon(couponId, IssuedCouponType.ADMIN);
    return ResponseEntity.ok(Collections.singletonMap("message", "쿠폰이 발급되었습니다!"));

  }

  /**
   * 가게 쿠폰 다운로드 api (POST).
   */
  @PostMapping("/download/owner/{couponId}")
  public ResponseEntity<?> downloadOwnerCoupon(@PathVariable Long couponId) {
    //현재 사용자의 userId를 가져와야함
    userCouponService.downloadCoupon(couponId, IssuedCouponType.OWNER);
    return ResponseEntity.ok(Collections.singletonMap("message", "쿠폰이 발급되었습니다!"));

  }

  /**
   * 선착순 쿠폰 다운로드 api (POST).
   */
  @PostMapping("/download/first-come/{couponId}")
  public ResponseEntity<?> downloadFirstComeCoupon(@PathVariable Long couponId) {
    //현재 사용자의 userId를 가져와야함
    userCouponService.downloadCoupon(couponId, IssuedCouponType.ADMIN);
    return ResponseEntity.ok(Collections.singletonMap("message", "선착순 쿠폰 다운로드에 성공했습니다!"));
  }

  /**
   * 사용자 쿠폰함 쿠폰 목록 조회 api (GET).
   */
  @GetMapping("/box")
  public ResponseEntity<List<UserCouponListResponseDto>> getBoxCoupon() {
    List<UserCouponListResponseDto> coupons = userCouponService.findNotUsedCoupons();
    return ResponseEntity.status(HttpStatus.OK).body(coupons);
  }





}
