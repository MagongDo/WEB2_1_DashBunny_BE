package com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderRatingResponseDto {
    private Long orderId;
    private Long rating;
    private String storeId;
}
