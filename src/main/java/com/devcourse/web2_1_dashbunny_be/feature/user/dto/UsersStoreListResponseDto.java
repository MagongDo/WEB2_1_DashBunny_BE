package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  private Long deliveryTip;
  private String minDeliveryTime;
  private String maxDeliveryTime;
  private Long discountPrice;
  private StoreStatus status;

  public UsersStoreListResponseDto toUsersStoreListResponseDto(StoreManagement store, DeliveryOperatingInfo deliveryOperatingInfo) {
    if (store == null) {
      return null;
    }
    UsersStoreListResponseDto dto = new UsersStoreListResponseDto();
    this.storeId = store.getStoreId();
    this.storeName = store.getStoreName();
    this.storeLogo = store.getStoreLogo();
    if (store.getStoreFeedback() != null) {
      this.rating = store.getStoreFeedback().getRating();
      this.reviewCount = store.getStoreFeedback().getReviewCount();
    }

    if (deliveryOperatingInfo != null) {
      this.deliveryTip = deliveryOperatingInfo.getDeliveryTip();
      this.minDeliveryTime = deliveryOperatingInfo.getMinDeliveryTime();
      this.maxDeliveryTime = deliveryOperatingInfo.getMaxDeliveryTime();
      this.discountPrice = store.maxDiscountPrice(); // 실제 할인 가격 필드로 수정
    }

    this.status = store.getStoreStatus();
    dto.status = store.getStoreStatus();
    return dto;
  }


}
