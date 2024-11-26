package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryInfoResponseDto {
    private String minDeliveryTime;
    private String maxDeliveryTime;
    private String deliveryAreaInfo;
}