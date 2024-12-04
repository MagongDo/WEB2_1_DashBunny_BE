package com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.controller.dto;

import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.DeclineReasonType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeclineOrdersResponseDto {
    private String storeId;
    private Long orderId;
    private DeclineReasonType declineReasonType;

    public static DeclineOrdersResponseDto fromEntity(Orders order,
                                                    DeclineReasonType declineReasonType) {
        return DeclineOrdersResponseDto.builder()
                .storeId(order.getStoreId())
                .orderId(order.getOrderId())
                .declineReasonType(declineReasonType)
                .build();
    }


}
