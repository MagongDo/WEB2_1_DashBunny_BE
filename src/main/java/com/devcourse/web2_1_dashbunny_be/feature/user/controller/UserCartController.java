package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCartResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCartController {

    private final UsersCartService cartService;

    @PostMapping("/items")
    public ResponseEntity<UsersCartResponseDto> addItemToCart(
            @RequestParam Long menuId,
            @RequestParam Long quantity,
            @RequestParam(required = false, defaultValue = "false") boolean overwrite,
            Principal principal) {
        return ResponseEntity.ok(cartService.addMenuToCart(principal.getName(), menuId, quantity, overwrite));
    }

    @PatchMapping("/items/{menuId}")
    public ResponseEntity<UsersCartResponseDto> updateItemQuantity(
            @PathVariable Long menuId,
            @RequestParam Long quantity,
            Principal principal) {
        return ResponseEntity.ok(cartService.updateItemQuantity(principal.getName(), menuId, quantity));
    }

    @GetMapping("/carts")
    public ResponseEntity<UsersCartResponseDto> getCart(Principal principal) {
        return ResponseEntity.ok(cartService.getCart(principal.getName()));
    }
    @PostMapping("/carts/checkout")
    public ResponseEntity<UsersCartResponseDto> checkoutCart(Principal principal) {
        UsersCartResponseDto cartDto = cartService.checkoutCart(principal.getName());
        return ResponseEntity.ok(cartDto);
    }
}

