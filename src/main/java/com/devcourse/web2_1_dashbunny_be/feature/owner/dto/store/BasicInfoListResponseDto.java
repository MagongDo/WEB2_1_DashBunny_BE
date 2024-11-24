package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.Builder;

/**
 * 가게 기본정보 응답을 위한 DTO 클래스.
 */
@Builder
public class BasicInfoListResponseDto {
  private String storeName;
  private String storePhone;
  private StoreStatus storeStatus;
  private String storeAddress;
  private String storeLogo;
  private String storeBannerImage;
  private String storeDescription;
  private String shortsUrl;

  /**
   *가게 엔티티를 디티오로 변환.
   */
  public BasicInfoListResponseDto fromEntity(BasicInfoProjection basicInfoProjection) {
    return BasicInfoListResponseDto.builder()
       .storeName(basicInfoProjection.getStoreName())
       .storePhone(basicInfoProjection.getContactNumber())
       .storeStatus(basicInfoProjection.getStoreStatus())
       .storeAddress(basicInfoProjection.getAddress())
       .storeLogo(basicInfoProjection.getStoreLogo())
       .storeBannerImage(basicInfoProjection.getStoreBannerImage())
       .storeDescription(basicInfoProjection.getDescription())
       .shortsUrl(basicInfoProjection.getShortsUrl())
       .build();
  }


}
