package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInfoResponseDto {
    private String minDeliveryTime; // 최소 배달 시간
    private String maxDeliveryTime; // 최대 배달 시간
    private String deliveryAreaInfo; // 배달 지역 안내
}