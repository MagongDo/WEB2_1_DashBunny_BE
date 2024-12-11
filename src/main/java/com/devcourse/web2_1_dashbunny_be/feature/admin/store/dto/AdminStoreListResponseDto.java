package com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto;


import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 가게 목록의 정보를 보여주는 dto.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminStoreListResponseDto {

  private String storeId;
  private String storeName;
  private String userPhone;
  private String description;
  private String address;
  private StoreStatus storeStatus;
  private String storeLogo;    // 가게 로고 이미지
  private String userName; //사장님 이름
  private LocalDateTime approvedDate; //승인 날짜


  /**
   * StoreManagement 엔티티에서 DTO로 데이터를 변환을 위한 생성자.
   */
  public AdminStoreListResponseDto(StoreManagement storeManagement) {
    this.storeId = storeManagement.getStoreId();
    this.storeName = storeManagement.getStoreName();
    this.userPhone = storeManagement.getUser().getPhone();
    this.description = storeManagement.getStoreDescription();
    this.address = storeManagement.getAddress();
    this.storeStatus = storeManagement.getStoreStatus();
    this.storeLogo = storeManagement.getStoreLogo();
    this.approvedDate = storeManagement.getApprovedDate();
    this.userName = storeManagement.getUser().getName();
  }
}
