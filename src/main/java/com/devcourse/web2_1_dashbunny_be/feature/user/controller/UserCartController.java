package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UserCartCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCartResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCheckCouponDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersCartCouponService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCartController {

  private final UsersCartService cartService; // 장바구니 관련 비즈니스 로직을 처리하는 서비스
  private final UserService userService; // 사용자 관련 정보를 관리하는 서비스
  private final UsersCartCouponService usersCartCouponService; //장바구니에서 보여주는 쿠폰 정보를 관리하는 서비스
  /**
   * POST /api/users/items
   * 장바구니에 메뉴를 추가하는 메소드.
   * @param menuId 추가할 메뉴의 ID
   * @param quantity 추가할 수량
   * @param overwrite 다른 가게 메뉴가 있는 경우 덮어쓰기 여부 (기본값: false)
   * @return 장바구니 상태를 담은 응답 DTO
   */
  @PostMapping("/items")
  public ResponseEntity<UsersCartResponseDto> addItemToCart(
          @RequestParam Long menuId,
          @RequestParam Long quantity,
          @RequestParam(required = false, defaultValue = "false") boolean overwrite,
          @RequestHeader("Authorization") String authorizationHeader
  ) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    return ResponseEntity.ok(cartService.addMenuToCart(currentUser.getPhone(), menuId, quantity, overwrite));
  }

  /**
   * PATCH /api/users/items/{menuId}
   * 장바구니에 담긴 특정 메뉴의 수량을 업데이트하는 메소드.
   * @param menuId 수량을 변경할 메뉴의 ID
   * @param quantity 변경할 수량 (양수: 추가, 음수: 감소)
   * @return 업데이트된 장바구니 상태를 담은 응답 DTO
   */
  @PatchMapping("/items/{menuId}")
  public ResponseEntity<UsersCartResponseDto> updateItemQuantity(
          @PathVariable Long menuId,
          @RequestParam Long quantity,
          @RequestHeader("Authorization") String authorizationHeader
  ) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    return ResponseEntity.ok(cartService.updateItemQuantity(currentUser.getPhone(), menuId, quantity));
  }

  /**
   * GET /api/users/carts
   * 현재 사용자의 장바구니 정보를 조회하는 메소드.
   * @return 장바구니 상태를 담은 응답 DTO.
   */
  @GetMapping("/carts")
  public ResponseEntity<UsersCartResponseDto> getCart(@RequestHeader("Authorization") String authorizationHeader) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    return ResponseEntity.ok(cartService.getCart(currentUser.getPhone()));
  }

  /**
   * GET /api/users/carts/coupons
   * 현재 사용자의 장바구니에 적용가능한 쿠폰 목록 조회.
   * @return 사용가능한 쿠폰 목록를 담은 응답 DTO.
   */
  @GetMapping("/carts/coupons")
  public ResponseEntity<List<UserCartCouponListResponseDto>> getCoupons(@RequestHeader("Authorization") String authorizationHeader) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    List<UserCartCouponListResponseDto> availableCoupons = usersCartCouponService.findCouponsInCart(currentUser.getUserId());
    return ResponseEntity.ok(availableCoupons);
  }

  @GetMapping("/carts/coupons/{userCouponId}")
  public ResponseEntity<UsersCartResponseDto> getCoupon(@PathVariable String userCouponId,
                                                        @RequestHeader("Authorization") String authorizationHeader) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    UsersCheckCouponDto checkCoupon = usersCartCouponService.selectCouponById(currentUser.getUserId(), userCouponId);

    // 현재 장바구니 정보 조회
    UsersCartResponseDto cartDto = cartService.getCart(currentUser.getPhone());
    cartDto.setCoupon(checkCoupon); // 선택된 쿠폰 정보 저장

    return ResponseEntity.ok(cartDto);
  }



  /**
   * POST /api/users/carts/checkout
   * 장바구니 결제를 처리하는 메소드.
   * @param storeRequirement 가게 요청사항
   * @param deliveryRequirement 배달 요청사항
   * @return 결제된 장바구니 상태를 담은 응답 DTO
   */
  @PostMapping("/carts/checkout")
  public ResponseEntity<UsersCartResponseDto> checkoutCart(@RequestParam String storeRequirement,
                                                           @RequestParam String deliveryRequirement,
                                                           @RequestHeader("Authorization") String authorizationHeader) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    UsersCartResponseDto cartDto = cartService.checkoutCart(currentUser.getPhone(),
            storeRequirement,
            deliveryRequirement);
    return ResponseEntity.ok(cartDto);
  }
}

