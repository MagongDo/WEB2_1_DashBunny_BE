package com.devcourse.web2_1_dashbunny_be.feature.order.service;

import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.*;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.user.UserOrderInfoRequestDto;


import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    Orders creatOrder(OrderInfoRequestDto orderInfoRequestDto);
//    CompletableFuture<Orders> creatOrder(OrderInfoRequestDto orderInfoRequestDto);
    CompletableFuture<AcceptOrdersResponseDto> acceptOrder(OrderAcceptRequestDto acceptRequestDto);
    CompletableFuture<DeclineOrdersResponseDto> declineOrder(OrderDeclineRequestDto declineRequestDto);
    List<UserOrderInfoRequestDto> getUserOrderInfoList(String userId);
}
