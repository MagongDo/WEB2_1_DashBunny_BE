package com.devcourse.web2_1_dashbunny_be.feature.user.dto.order;

import com.devcourse.web2_1_dashbunny_be.domain.user.OrderItem;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCartItemDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCartResponseDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderItemDto 를 기반으로 주문 요청을 위한 dto.
 */
public class OrderInfoRequestDto {

  private Long orderId; // 주문 ID
  private List<OrderItemDto> orderItems; // 주문 항목
  private LocalDateTime orderDate; // 주문 날짜
  private Long deliveryPrice; // 배달 가격
  private String deliveryAddress; // 배달 주소
  private String storeNote; // 사장님에게 전달할 메모
  private String riderNote; // 라이더에게 전달할 메모

}