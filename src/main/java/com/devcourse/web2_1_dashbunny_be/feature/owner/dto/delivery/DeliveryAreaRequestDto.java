package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import lombok.Data;

@Data
public class DeliveryAreaRequestDto {
    private String deliveryRange;

    public DeliveryOperatingInfo toEntity(DeliveryOperatingInfo existingEntity) {
        existingEntity.setDeliveryRange(this.deliveryRange);
        return existingEntity;
    }
}