package com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto;

import com.devcourse.web2_1_dashbunny_be.domain.user.OrderItem;
import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 주문 접수 후 실시간으로 사장님에게 전달되는 알람 내용을 담은 Dto.
 */
@Getter
@Setter
@Builder
public class StoreOrderAlarmResponseDto {

  private int totalMenuCount;
  private List<String> menuNames;
  private Long totalPrice;

  /**
   * 사장님의 알람창에 필요한 필드.
   */
  public static StoreOrderAlarmResponseDto fromEntity(Orders orders) {

    List<OrderItem> orderItems = orders.getOrderItems();
    List<String> menuNames = orderItems.stream()
        .map(orderItem -> orderItem.getMenu().getMenuName())
        .toList();

    return StoreOrderAlarmResponseDto.builder()
        .totalMenuCount(orders.getTotalMenuCount())
        .menuNames(menuNames)
        .totalPrice(orders.getTotalPrice())
        .build();
  }
}
