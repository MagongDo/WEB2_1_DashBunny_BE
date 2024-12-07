package com.devcourse.web2_1_dashbunny_be.feature.order.controller;

import lombok.Builder;

import java.util.List;

@Builder
public class OrdersListResponseDto {
    private OrderDetailDto orderDetail;
    private List<OrderListDto>orderList;


    public OrdersListResponseDto(OrderDetailDto orderDetail, List<OrderListDto> orderList) {
        this.orderDetail = orderDetail;
        this.orderList = orderList;
    }
}
