package com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto;

import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderListDto implements Serializable {
    private List<String> menuName; // 주문 메뉴 이름 리스트
    private int preparationTime; // 사장님이 설정한 예상 시간
    private Long totalPrice; // 주문 총 금액

    public static OrderListDto fromEntity(Orders orders) {
        return OrderListDto.builder()
                .menuName(orders.getOrderItems().stream().map(menu -> menu.getMenu().getMenuName()).toList())
                .preparationTime(orders.getPreparationTime())
                .totalPrice(orders.getTotalPrice())
                .build();
    }
}
