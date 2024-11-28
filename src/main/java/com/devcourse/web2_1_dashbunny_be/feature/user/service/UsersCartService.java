package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.domain.user.CartItem;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;

import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.DeliveryOperatingInfoRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCartResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersCartService {

    private final UsersCartRepository cartRepository;
    private final MenuRepository menuManagementRepository;
    private final StoreManagementRepository storeManagementRepository;
    private final DeliveryOperatingInfoRepository deliveryOperationInfoRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public void createCart(String userId) {
        User user = userRepository.findByPhone(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (cartRepository.findByUser(user) == null) {
            Cart cart = Cart.builder().user(user).build();
            cartRepository.save(cart);
        }
    }

    public UsersCartResponseDto getCart(String userId) {
        User user = userRepository.findByPhone(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            createCart(userId);
            cart = cartRepository.findByUser(user);
        }
        if (cart.getStoreId() == null) {
            return null;
        } else {
            StoreManagement store = storeManagementRepository.findById(cart.getStoreId()).orElseThrow(IllegalArgumentException::new);

            DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperationInfoRepository.findByStoreId(store.getStoreId());
            return UsersCartResponseDto.toUsersCartDto(cart, store.getStoreName(), deliveryOperatingInfo.getDeliveryTip(),null);


        }

    }

    @Transactional
    public UsersCartResponseDto addMenuToCart(String userId, Long menuId, Long quantity, boolean overwrite) {
        if (quantity <= 0) throw new IllegalArgumentException("수량은 0보다 커야 합니다.");

        User user = userRepository.findByPhone(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Cart cart = cartRepository.findByUser(user);

        // MenuManagement menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));


        if (cart == null) {
            // 카트가 없으면 새로 생성
            cart = Cart.builder()
                    .user(user)
                    .cartItems(new ArrayList<>())
                    .build();
            cartRepository.save(cart);
        }

        MenuManagement menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        StoreManagement store = storeManagementRepository.findById(menu.getStoreId())
                .orElseThrow(IllegalArgumentException::new);
        DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperatingInfoRepository.findByStoreId(store.getStoreId());

        if (cart.getStoreId() == null || cart.getStoreId().equals(store.getStoreId())) {
            // 카트에 가게 정보가 없거나, 가게가 동일한 경우
            cart.setStoreId(store.getStoreId());
        } else {
            // 카트에 다른 가게의 메뉴가 담겨 있는 경우
            if (overwrite) {
                // 카트를 초기화하고 새로운 가게로 설정
                cart.getCartItems().clear();
                cart.setStoreId(store.getStoreId());
            } else {
                // 메뉴 추가를 거부하고 예외를 발생시킴
                throw new IllegalArgumentException("현재 장바구니에 다른 가게의 메뉴가 담겨 있습니다.");
            }
        }

        // 카트 아이템 추가 또는 수량 업데이트
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
        cartRepository.save(cart);

        return UsersCartResponseDto.toUsersCartDto(cart, store.getStoreName(), deliveryOperatingInfo.getDeliveryTip(),null);
    }

    public UsersCartResponseDto updateItemQuantity(String userId, Long menuId, Long counts) {


        User user = userRepository.findByPhone(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Cart cart = cartRepository.findByUser(user);
        MenuManagement menu = menuRepository.findById(menuId).orElseThrow(IllegalArgumentException::new);
        StoreManagement store = storeManagementRepository.findById(menu.getStoreId()).orElseThrow(IllegalArgumentException::new);
        DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperatingInfoRepository.findByStoreId(store.getStoreId());
        List<CartItem> itemsToRemove = new ArrayList<>();

        cart.getCartItems().forEach(item -> {
            if (item.getMenuManagement().getMenuId().equals(menuId)) {

                long quantity = item.getQuantity() + counts;
                if (quantity <= 0) {
                    itemsToRemove.add(item);
                } else item.setQuantity(quantity);
            }
        });

        cart.getCartItems().removeAll(itemsToRemove);
        if (cart.getCartItems().isEmpty()) {
            cart.setStoreId(null);
            cart.setTotalPrice(null);
            cartRepository.save(cart);
            return getCart(userId);
        } else {
            cart.setTotalPrice(calculateTotalPrice(cart));
            cartRepository.save(cart);
            return UsersCartResponseDto.toUsersCartDto(cart, store.getStoreName(), deliveryOperatingInfo.getDeliveryTip(),null);
        }
    }

    private Long calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .mapToLong(item -> item.getQuantity() * item.getMenuManagement().getPrice())
                .sum();
    }

    public UsersCartResponseDto checkoutCart(String userId) {
        UsersCartResponseDto cartDto = getCart(userId);
        if (cartDto == null || cartDto.getCartItems() == null || cartDto.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        PaymentRequestDto paymentRequest = new PaymentRequestDto();
        paymentRequest.setCartId(cartDto.getCartId());
        paymentRequest.setOrderName("Order for Cart ID: " + cartDto.getCartId());

        PaymentResponseDto paymentResponse = paymentService.createPayment(paymentRequest);

        // Cart 엔티티 조회
        Cart cart = cartRepository.findById(cartDto.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        // 새로운 UsersCartResponseDto 생성하여 반환
        return UsersCartResponseDto.toUsersCartDto(
                cart,
                cartDto.getStoreName(),
                cartDto.getDeliveryFee(),
                paymentResponse // 결제 정보 전달
        );
    }

}
