package com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart;

import com.devcourse.web2_1_dashbunny_be.domain.user.CartItem;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UsersCartItemDto {
  private Long cartItemId;       // 장바구니 아이템 ID
  private Long menuId;           // 메뉴 ID
  private String menuName;       // 메뉴 이름
  private String menuImage;      // 메뉴 이미지 URL
  private Long price;            // 단가
  private Integer quantity;      // 수량
  private Long totalPrice;       // 총 금액 (단가 * 수량)

  public static UsersCartItemDto toUsersCartItemDto(CartItem cartItem) {
    return UsersCartItemDto.builder()
      .cartItemId(cartItem.getCartItemId())  // 장바구니 아이템 ID
      .menuId(cartItem.getMenuManagement().getMenuId())  // 메뉴 ID
      .menuName(cartItem.getMenuManagement().getMenuName())  // 메뉴 이름
      .menuImage(cartItem.getMenuManagement().getMenuImage())  // 메뉴 이미지 URL
      .price(cartItem.getMenuManagement().getPrice())  // 단가
      .quantity(cartItem.getQuantity().intValue())  // 수량
      .totalPrice(cartItem.getMenuManagement().getPrice() * cartItem.getQuantity())  // 총 금액
      .build();
  }

}
