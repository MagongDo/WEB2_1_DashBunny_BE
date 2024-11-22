package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.OrderInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.UpdateOrderInfoRequestDto;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoService {

    public OrderInfoResponseDto getOrderInfo() {
        return new OrderInfoResponseDto("On", 500, 10000, 3000);
    }

    public void updateOrderInfo(UpdateOrderInfoRequestDto updateOrderInfo) {
    }
}