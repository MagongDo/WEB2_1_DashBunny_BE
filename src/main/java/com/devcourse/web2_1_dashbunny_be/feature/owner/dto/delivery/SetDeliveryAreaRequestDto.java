package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetDeliveryAreaRequestDto {
    private String deliveryRange; // 설정할 배달 범위
}
