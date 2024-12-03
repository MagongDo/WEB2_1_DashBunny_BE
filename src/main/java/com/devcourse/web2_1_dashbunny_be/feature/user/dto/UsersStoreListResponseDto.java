package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreFeedBack;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.*;

@Getter
@Setter
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
  private String shortsUrl;

  public static UsersStoreListResponseDto toUsersStoreListResponseDto(StoreManagement store,
                                                                      DeliveryOperatingInfo deliveryOperatingInfo,
                                                                      StoreFeedBack storeFeedBack) {
    if (store == null) {
      return null;
    }

    UsersStoreListResponseDto dto = new UsersStoreListResponseDto();
    dto.setStoreId(store.getStoreId());
    dto.setStoreName(store.getStoreName());
    dto.setStoreLogo(store.getStoreLogo());

    if (storeFeedBack != null) {
      dto.setRating(storeFeedBack.getRating());
      dto.setReviewCount(storeFeedBack.getReviewCount());
    }

    if (deliveryOperatingInfo != null) {
      dto.setDeliveryTip(deliveryOperatingInfo.getDeliveryTip());
      dto.setMinDeliveryTime(deliveryOperatingInfo.getMinDeliveryTime());
      dto.setMaxDeliveryTime(deliveryOperatingInfo.getMaxDeliveryTime());
      dto.setDiscountPrice(store.maxDiscountPrice()); // 실제 할인 가격 필드로 수정
    }

    // StoreStatus가 Enum이라면 name()을 사용하여 문자열로 변환
    dto.setStatus(store.getStoreStatus() != null ? store.getStoreStatus() : null);

    return dto;
  }

  public static UsersStoreListResponseDto toUsersStoreShortsListResponseDto(StoreManagement store,
                                                                            StoreOperationInfo storeOperationInfo,
                                                                            StoreFeedBack storeFeedBack) {
    if (store == null) {
      return null;
    }

    UsersStoreListResponseDto dto = new UsersStoreListResponseDto();
    dto.setStoreId(store.getStoreId());
    dto.setStoreName(store.getStoreName());
    dto.setStoreLogo(store.getStoreLogo());

    if (storeFeedBack != null) {
      dto.setRating(storeFeedBack.getRating());
      dto.setReviewCount(storeFeedBack.getReviewCount());
    }

    if (storeOperationInfo != null) {
      dto.setShortsUrl(storeOperationInfo.getShortsUrl());
    }

    // StoreStatus가 Enum이라면 name()을 사용하여 문자열로 변환
    dto.setStatus(store.getStoreStatus() != null ? store.getStoreStatus() : null);

    return dto;
  }
}
