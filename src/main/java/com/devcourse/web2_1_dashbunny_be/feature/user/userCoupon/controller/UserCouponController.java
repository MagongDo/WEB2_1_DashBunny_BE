package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.controller;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service.AdminCouponService;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service.OwnerCouponService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.CustomUserDetailsService;
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
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * 관리자 발급한 일반 쿠폰 목록 조회 api (GET).
     */
    @GetMapping("/general")
    public ResponseEntity<List<GeneralCouponListResponseDto>> getUserCoupon() {
        List<GeneralCouponListResponseDto> coupons=userCouponService.findActiveRegularCoupons();
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }

    /**
     * 관리자 발급한 선착순 쿠폰  조회 api (GET).
     */


    /**
     * 사장님이 발급한 쿠폰 목록 조회 api (GET).
     */


    /**
     * 일반 쿠폰 다운로드 api (POST).
     */
    @PostMapping("/download/general/{couponId}")
    public ResponseEntity<?> downloadGeneralCoupon(@PathVariable Long couponId) {
        //현재 사용자의 userId를 가져와야함
        userCouponService.downloadCoupon(couponId, IssuedCouponType.ADMIN);

    }

    /**
     * 가게 쿠폰 다운로드 api (POST).
     */
    @PostMapping("/download/owner/{couponId}")
    public ResponseEntity<?> downloadGeneralCoupon(@PathVariable Long couponId) {
        //현재 사용자의 userId를 가져와야함
        userCouponService.downloadCoupon(couponId, IssuedCouponType.OWNER);

    }

    /**
     * 선착순 쿠폰 다운로드 api (POST).
     */
    @PostMapping("/download/firstCome/{couponId}")
    public ResponseEntity<?> downloadGeneralCoupon(@PathVariable Long couponId) {
        //현재 사용자의 userId를 가져와야함
        userCouponService.downloadCoupon(couponId, IssuedCouponType.ADMIN);

    }

    /**
     * 사용자 쿠폰함 쿠폰 목록 조회 api (GET)
     */




}
