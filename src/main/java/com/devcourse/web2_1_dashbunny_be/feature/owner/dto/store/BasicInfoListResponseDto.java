package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.Builder;
import org.springframework.context.annotation.Bean;

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
  public BasicInfoListResponseDto fromEntity(StoreManagement storeManagement) {
    return BasicInfoListResponseDto.builder()
       .storeName(storeManagement.getStoreName())
       .storePhone(storeManagement.getStorePhone())
       .storeStatus(storeManagement.getStoreStatus())
       .storeAddress(storeManagement.getStoreAddress())
       .storeLogo(storeManagement.getStoreLogo())
       .storeBannerImage(storeManagement.getStoreBannerImage())
       .storeDescription(storeManagement.getDescription())
       .shortsUrl(storeManagement.getShortsUrl())
       .build();
  }


}
