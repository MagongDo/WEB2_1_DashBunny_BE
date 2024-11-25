package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersStoreListResponseDto {
    private String storeId;
    private String storeName;
    private String storeLogo;
    private double rating;
    private int reviewCount;
    private Long baseDeliveryTip;
    private String minDeliveryTime;
    private String maxDeliveryTime;
    private BigDecimal discountPrice;
    private StoreStatus status;

    public void toUsersStoreListResponseDto(StoreManagement store) {
        if (store == null) {
            return;
        }
        this.storeId = store.getStoreId();
        this.storeName = store.getStoreName();
        this.storeLogo = store.getStoreLogo();
        if (store.getStoreFeedback() != null) {
            this.rating = store.getStoreFeedback().getRating();
            this.reviewCount = store.getStoreFeedback().getReviewCount();
        }

        if (store.getDeliveryInfo() != null) {
            this.baseDeliveryTip = store.getDeliveryInfo().getBaseDeliveryTip();
            this.minDeliveryTime = store.getDeliveryInfo().getMinDeliveryTime();
            this.maxDeliveryTime = store.getDeliveryInfo().getMaxDeliveryTime();
            this.discountPrice = store.maxDiscountPrice(); // 실제 할인 가격 필드로 수정
        }

        this.status = store.getStoreStatus();
    }

}
