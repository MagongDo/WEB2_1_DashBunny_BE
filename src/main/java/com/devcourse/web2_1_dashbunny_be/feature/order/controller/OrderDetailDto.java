package com.devcourse.web2_1_dashbunny_be.feature.order.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.OrderStatus;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.OrderItemDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Setter;

/**
 * 주문 단 건 조회를 위한 dto.
 */
@Setter
@Builder
public class OrderDetailDto {

  private Long orderId; // 주문 아이디
  private Long totalPrice; //전체 메뉴에 대한 총 금액
  private LocalDateTime orderDate; // 주문 시간
  private OrderStatus orderStatus; // 주문 상태
  private String storeNote; // 사장님 요청 사항
  private List<OrderItemDto> orderItems; // 단 건 주문 상세
  private int preparationTime; // 사장님이 설정한 주문 예상 시간

  /**
  * 엔티티를 dto 로 반환.
  */
  public static OrderDetailDto fromEntity(Orders order, List<OrderItemDto> orderItems) {
    return OrderDetailDto.builder()
        .orderId(order.getOrderId())
        .totalPrice(order.getTotalPrice())
        .orderDate(order.getOrderDate())
        .orderStatus(order.getOrderStatus())
        .storeNote(order.getStoreNote())
        .orderItems(orderItems)
        .preparationTime(order.getPreparationTime())
        .build();
    }
}



