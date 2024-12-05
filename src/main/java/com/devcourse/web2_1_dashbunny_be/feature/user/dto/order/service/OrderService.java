package com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.service;

import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.controller.dto.*;

import java.util.concurrent.CompletableFuture;

public interface OrderService {

    CompletableFuture<Orders> creatOrder(OrderInfoRequestDto orderInfoRequestDto);
    CompletableFuture<AcceptOrdersResponseDto> acceptOrder(OrderAcceptRequestDto acceptRequestDto);
    CompletableFuture<DeclineOrdersResponseDto> declineOrder(OrderDeclineRequestDto declineRequestDto);
}
