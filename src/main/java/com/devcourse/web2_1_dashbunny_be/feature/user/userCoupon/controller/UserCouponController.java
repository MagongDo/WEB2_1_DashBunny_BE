package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.controller;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service.AdminCouponService;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service.OwnerCouponService;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.GeneralCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 관리자 발급한 일반 쿠폰 조회 api (GET).
     */
    @GetMapping("/general")
    public ResponseEntity<List<GeneralCouponListResponseDto>> getUserCoupon() {
        List<GeneralCouponListResponseDto> coupons=userCouponService.findActiveRegularCoupons();
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }

    /**
     * 일반 쿠폰 다운로드 api (POST).
     */
//    @PostMapping("/general/download/{couponId}")
//    public ResponseEntity<?> downloadGeneralCoupon(@PathVariable Long couponId) {
//        userCouponService.
//
//    }

    /**
     * 사용자 쿠폰함 쿠폰 목록 조회 api (GET)
     */



}
