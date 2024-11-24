package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import lombok.Data;

@Data
public class OrderInfoRequestDto {
    private Long takeoutDiscount;
    private Long minOrderAmount;
    private Long deliveryTip;

    public DeliveryOperatingInfo toEntity(DeliveryOperatingInfo existingEntity) {
        existingEntity.setTakeoutDiscount(this.takeoutDiscount);
        existingEntity.setMinOrderAmount(this.minOrderAmount);
        existingEntity.setDeliveryTip(this.deliveryTip);
        return existingEntity;
    }
}