package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class UsersStoreListResponseDto {
    private String storeId;
    private String storeName;
    private double rating;
    private int reviewCount;
    private Long baseDeliveryTip;
    private String minDeliveryTime;
    private String maxDeliveryTime;
    private BigDecimal discountPrice;
    private StoreStatus status;

    public UsersStoreListResponseDto toDTO(StoreManagement storeManagement) {
        return UsersStoreListResponseDto.builder()
                .storeId(storeManagement.getStoreId())
                .storeName(storeManagement.getStoreName())
                .rating(storeManagement.getStoreFeedback().getRating())
                .reviewCount(storeManagement.getStoreFeedback().getReviewCount())
                .baseDeliveryTip(storeManagement.getDeliveryInfo().getBaseDeliveryTip())
                .minDeliveryTime(storeManagement.getDeliveryInfo().getMinDeliveryTime())
                .maxDeliveryTime(storeManagement.getDeliveryInfo().getMaxDeliveryTime())
                .discountPrice(storeManagement.maxDiscountPrice())
                .status(storeManagement.getStoreStatus())
                .build();
    }

}
