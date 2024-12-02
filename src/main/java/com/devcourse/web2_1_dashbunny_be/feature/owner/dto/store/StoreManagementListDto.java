package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class StoreManagementListDto {

  private String storeId;
  private String storeName;
  private String storeStatus;

  public static StoreManagementListDto fromEntity(StoreManagement storeManagement) {
      return StoreManagementListDto.builder()
              .storeId(storeManagement.getStoreId())
              .storeName(storeManagement.getStoreName())
              .storeStatus(storeManagement.getStoreStatus().toString())
              .build();
  }
}
