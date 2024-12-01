package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCartResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersCartService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCartController {

  private final UsersCartService cartService;
  private final UserService userService;

  @PostMapping("/items")
  public ResponseEntity<UsersCartResponseDto> addItemToCart(
          @RequestParam Long menuId,
          @RequestParam Long quantity,
          @RequestParam(required = false, defaultValue = "false") boolean overwrite
          ) {
    User currentUser = userService.getCurrentUser();
    return ResponseEntity.ok(cartService.addMenuToCart(currentUser.getPhone(), menuId, quantity, overwrite));
  }

  @PatchMapping("/items/{menuId}")
  public ResponseEntity<UsersCartResponseDto> updateItemQuantity(
          @PathVariable Long menuId,
          @RequestParam Long quantity
          ) {
    User currentUser = userService.getCurrentUser();
    return ResponseEntity.ok(cartService.updateItemQuantity(currentUser.getPhone(), menuId, quantity));
  }

  @GetMapping("/carts")
  public ResponseEntity<UsersCartResponseDto> getCart() {
    User currentUser = userService.getCurrentUser();
    return ResponseEntity.ok(cartService.getCart(currentUser.getPhone()));
  }
  @PostMapping("/carts/checkout")
  public ResponseEntity<UsersCartResponseDto> checkoutCart() {
    User currentUser = userService.getCurrentUser();
    UsersCartResponseDto cartDto = cartService.checkoutCart(currentUser.getPhone());
    return ResponseEntity.ok(cartDto);
  }
}

