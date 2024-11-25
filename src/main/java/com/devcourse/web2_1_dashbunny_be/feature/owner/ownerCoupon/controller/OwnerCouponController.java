package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service.OwnerCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store/coupon")
public class OwnerCouponController {
    private final OwnerCouponService ownerCouponService;
}
