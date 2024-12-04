package com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.controller.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.OrderItem;
import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderItemDto 를 기반으로 주문 요청을 위한 dto.
 */
@Getter
@Setter
@Builder
public class OrderInfoRequestDto {

  private String storeId; // 가게 ID
  private String userPhone;
  private List<OrderItemDto> orderItems; // 주문 항목
  private LocalDateTime orderDate; // 주문 날짜
  private Long deliveryPrice; // 배달 가격
  private String deliveryAddress; // 배달 주소
  private String storeNote; // 사장님에게 전달할 메모
  private String riderNote; // 라이더에게 전달할 메모

  public Orders toEntity(List<OrderItemDto> orderItems, MenuRepository menuRepository, User user) {
    // OrderItemDto를 OrderItem으로 변환
    List<OrderItem> orderItemList = orderItems.stream()
            .map(orderItemDto -> {
              // MenuManagement를 menuId로 조회
              MenuManagement menu = menuRepository.findById(orderItemDto.getMenuId())
                      .orElseThrow(() -> new IllegalArgumentException("Invalid menu ID: " + orderItemDto.getMenuId()));
              return orderItemDto.toEntity(menu);
            }).toList();

    Orders orders = new Orders();
    orders.setStoreId(this.storeId);
    orders.setUser(user);
    orders.setOrderDate(this.orderDate);
    orders.setOrderItems(orderItemList);
    orders.setDeliveryPrice(this.deliveryPrice);
    orders.setDeliveryAddress(this.deliveryAddress);
    orders.setStoreNote(this.storeNote);
    orders.setRiderNote(this.riderNote);

    // 양방향 관계 설정
    orderItemList.forEach(orderItem -> orderItem.setOrder(orders));

    return orders;
  }
}
//클래스 리펙토링 시급