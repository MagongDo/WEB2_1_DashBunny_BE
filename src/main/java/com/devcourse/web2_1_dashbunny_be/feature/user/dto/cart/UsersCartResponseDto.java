package com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart;

import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * 사용자에게 장바구니 내용을 보여주기 위한 조회용 dto.
 */
@Builder
@Getter
@Setter
public class UsersCartResponseDto {
  private Long cartId;
  private Long userId;                   // 사용자 ID
  private String storeName;              // 가게 이름
  private List<UsersCartItemDto> cartItems;   // 장바구니 아이템 리스트
  private Long deliveryFee;              // 배달료
  private Long discountPrice;                   //할인 금액
  private Long totalAmount;              // 총 결제 금액 (주문 금액 + 배달료 - 할인금액)
  private PaymentResponseDto paymentInfo;
  private String storeRequirement;
  private String deliveryRequirement;

  private UsersCheckCouponDto coupon;    // 선택된 쿠폰 정보 추가

  public static UsersCartResponseDto toUsersCartDto(Cart cart,
                                                    String storeName,
                                                    Long deliveryFee,
                                                    PaymentResponseDto paymentInfo) {
    List<UsersCartItemDto> cartItemDtos = cart.getCartItems().stream()
              .map(UsersCartItemDto::toUsersCartItemDto)  // 각 CartItem을 UsersCartItemDto로 변환
              .toList();

    // 전체 금액 계산: 장바구니 항목 합계
    Long totalPrice = cartItemDtos.stream()
              .mapToLong(UsersCartItemDto::getTotalPrice)
              .sum();

    // 최종 결제 금액 계산
    Long totalAmount = totalPrice + deliveryFee;

    return UsersCartResponseDto.builder()
              .cartId(cart.getCartId())
              .userId(cart.getUser().getUserId())  // 사용자 ID
              .storeName(storeName)  // 가게 이름
              .cartItems(cartItemDtos)  // 장바구니 아이템 리스트
              .deliveryFee(deliveryFee)  // 배달료
              .totalAmount(totalAmount)  // 총 결제 금액
              .paymentInfo(paymentInfo)
              .build();
  }
}
