package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.domain.user.CartItem;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;

import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersCartService {

    private final UsersCartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final StoreManagementRepository storeManagementRepository;
    private final UserRepository userRepository;
    // 장바구니 생성
    public Cart createCart(String userId) {
        User user= userRepository.findByPhone(userId).orElseThrow(IllegalArgumentException::new);
        if(cartRepository.findByUser(user)==null) {
            Cart cart = Cart.builder()
                    .user(user)
                    .build();
            return cartRepository.save(cart);
        }
        return null;
    }

    // 장바구니 조회
    public Cart getCart(String userId) {
        User user= userRepository.findByPhone(userId).orElseThrow(IllegalArgumentException::new);
        return cartRepository.findByUser(user);
    }
    // 메뉴 추가
    public Cart addMenuToCart(String userId, Long menuId, Long quantity) {
        User user= userRepository.findByPhone(userId).orElseThrow(IllegalArgumentException::new);
        Cart cart = cartRepository.findByUser(user);
        MenuManagement menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getMenuManagement().getMenuId().equals(menuId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .menuManagement(menu)
                    .quantity(quantity)
                    .build();
            cart.getCartItems().add(newItem);
        }

        cart.setTotalPrice(calculateTotalPrice(cart));
        return cartRepository.save(cart);
    }

    // 수량 조정
    public Cart updateItemQuantity(String userId, Long menuId, Long quantity) {
        User user= userRepository.findByPhone(userId).orElseThrow(IllegalArgumentException::new);
        Cart cart = cartRepository.findByUser(user);
        cart.getCartItems().forEach(item -> {
            if (item.getMenuManagement().getMenuId().equals(menuId)) {
                if (quantity <= 0) {
                    cart.getCartItems().remove(item);
                } else {
                    item.setQuantity(quantity);
                }
            }
        });

        cart.setTotalPrice(calculateTotalPrice(cart));
        return cartRepository.save(cart);
    }

    private Long calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .mapToLong(item -> item.getQuantity() * item.getMenuManagement().getPrice())
                .sum();
    }
}
