package com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart;

import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.domain.user.CartItem;
import lombok.Builder;

import java.util.List;

@Builder
public class UsersCartResponseDto {
    private Long cartId;                   // 장바구니 ID
    private Long userId;                   // 사용자 ID
    private String storeName;              // 가게 이름
    private List<UsersCartItemDto> cartItems;   // 장바구니 아이템 리스트
    private Long deliveryFee;              // 배달료
//    private Long discountAmount;           // 할인 금액
    private Long totalAmount;              // 총 결제 금액 (주문 금액 + 배달료 - 할인 금액)

    public static UsersCartResponseDto toUsersCartDto(Cart cart) {
        List<UsersCartItemDto> cartItemDtos = cart.getCartItems().stream()
                .map(UsersCartItemDto::toUsersCartItemDto)  // 각 CartItem을 UsersCartItemDto로 변환
                .toList();

        // 전체 금액 계산: 장바구니 항목 합계
        Long totalPrice = cartItemDtos.stream()
                .mapToLong(UsersCartItemDto::getTotalPrice)
                .sum();

        // 최종 결제 금액 계산
        Long totalAmount = totalPrice + cart.getStoreId().getDeliveryInfo().getBaseDeliveryTip();
                //- discountAmount;

        return UsersCartResponseDto.builder()
                .cartId(cart.getCartId())  // 장바구니 ID
                .userId(cart.getUser().getUserId())  // 사용자 ID
                .storeName(cart.getStoreId().getStoreName())  // 가게 이름
                .cartItems(cartItemDtos)  // 장바구니 아이템 리스트
                .deliveryFee(cart.getStoreId().getDeliveryInfo().getBaseDeliveryTip())  // 배달료
//                .discountAmount(discountAmount)  // 할인 금액
                .totalAmount(totalAmount)  // 총 결제 금액
                .build();
    }

}
