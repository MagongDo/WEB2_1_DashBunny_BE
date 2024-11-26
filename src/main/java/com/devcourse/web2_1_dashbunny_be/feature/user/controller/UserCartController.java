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
            Principal principal) {
        Cart updatedCart = cartService.addMenuToCart(principal.getName(), menuId, quantity);
        return ResponseEntity.ok(UsersCartResponseDto.toUsersCartDto(updatedCart));
    }

    @PatchMapping("/items/{menuId}")
    public ResponseEntity<UsersCartResponseDto> updateItemQuantity(
            @PathVariable Long menuId,
            @RequestParam Long quantity,
            Principal principal) {
        Cart updatedCart = cartService.updateItemQuantity(principal.getName(), menuId, quantity);
        return ResponseEntity.ok(UsersCartResponseDto.toUsersCartDto(updatedCart));
    }

    @GetMapping("/carts")
    public ResponseEntity<UsersCartResponseDto> getCart(Principal principal) {
        Cart cart = cartService.getCart(principal.getName());
        return ResponseEntity.ok(UsersCartResponseDto.toUsersCartDto(cart));
    }

    @PostMapping("/carts/create")
    public ResponseEntity<UsersCartResponseDto> createCart(Principal principal) {
        Cart newCart = cartService.createCart(principal.getName());
        return ResponseEntity.ok(UsersCartResponseDto.toUsersCartDto(newCart));
    }
}
