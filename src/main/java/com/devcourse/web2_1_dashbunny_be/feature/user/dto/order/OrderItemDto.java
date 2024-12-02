package com.devcourse.web2_1_dashbunny_be.feature.user.dto.order;

import com.devcourse.web2_1_dashbunny_be.domain.user.OrderItem;

/**
 * 장바구니에서 결제가 완료된 상품들의 정보가 담기는 dto.
 */
public class OrderItemDto {

  private Long menuId; // 메뉴 ID
  private String menuName; // 메뉴 이름
  private Integer quantity; // 수량
  private Long price; // 단가
  private Long totalPrice; // 총 금액 (단가 * 수량)

  public OrderItem toEntity(){
    return new OrderItem();
  }
}
