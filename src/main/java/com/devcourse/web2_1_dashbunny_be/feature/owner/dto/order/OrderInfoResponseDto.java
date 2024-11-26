package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderInfoResponseDto {
    private String isTakeout;
    private Long takeoutDiscount;
    private Long minOrderAmount;
    private Long deliveryTip;
}