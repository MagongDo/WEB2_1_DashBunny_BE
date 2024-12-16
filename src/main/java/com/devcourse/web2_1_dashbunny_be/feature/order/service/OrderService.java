package com.devcourse.web2_1_dashbunny_be.feature.order.service;

import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.OrdersListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.*;
import com.order.generated.ordersListResponseProtobuf;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.user.UserOrderInfoRequestDto;


import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    CompletableFuture<Orders> creatOrder(OrderInfoRequestDto orderInfoRequestDto);
    CompletableFuture<AcceptOrdersResponseDto> acceptOrder(OrderAcceptRequestDto acceptRequestDto);
    CompletableFuture<DeclineOrdersResponseDto> declineOrder(OrderDeclineRequestDto declineRequestDto);
    ordersListResponseProtobuf.OrdersListResponse getOrdersList(String storeId);
    List<UserOrderInfoRequestDto> getUserOrderInfoList(String userId);
    void increaseRating(OrderRatingResponseDto oders);
    OrdersListResponseDto getOrdersList(String storeId);
}
