package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.controller;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon.CreateOwnerCouponRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon.OwnerCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service.OwnerCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사장님 쿠폰 관리를 위한 api controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class OwnerCouponController {

  private final OwnerCouponService ownerCouponService;

  /**
  * 가게 아이디에 따른 쿠폰 목록 리스트 반환 api.
  */
  @GetMapping("/coupon/{storeId}")
  public ResponseEntity<List<OwnerCouponListResponseDto>> getOwnerCoupons(
            @PathVariable("storeId") String storeId) {
    List<OwnerCouponListResponseDto> responseDto = ownerCouponService.getCounponList(storeId);
    return ResponseEntity.ok(responseDto);
  }

  /**
  * 쿠폰 사용 종료 요청 api.
  */
  @PatchMapping("/coupon/{couponId}")
  public ResponseEntity<String> updateOwnerCoupon(@PathVariable("couponId") Long couponId) {
    ownerCouponService.updateCouponStatus(couponId);
    return ResponseEntity.ok(
            "해당 쿠폰은 조기 종료되었습니다.이미 쿠폰을 소지하고 있는 사용자는 유효 기간 내에 계속 사용할 수 있습니다. 자세한 내용은 고객센터로 문의해주세요.");
  }

  /**
  * 새로운 쿠폰 발급을 위한 요청 api.
  */
  @PostMapping("/coupon/{storeId}")
  public ResponseEntity<String> createCoupon(
            @PathVariable("storeId") String storeId,
            @RequestBody CreateOwnerCouponRequestDto createOwnerCouponRequestDto) {
    int day = createOwnerCouponRequestDto.getExpiredDate();
    OwnerCoupon ownerCoupon = createOwnerCouponRequestDto.toEntity();
    ownerCouponService.saveOwnerCoupon(ownerCoupon,day);
    return ResponseEntity.ok("쿠폰 생성이 완료 되었습니다");
    }
}
