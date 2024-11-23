package com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto;


import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 가게 목록의 정보를 보여주는 dto.
 */
@Getter
@NoArgsConstructor
public class AdminStoreListRequestDto {

  private String storeId;
  private String storeName;
  private String contactNumber;
  private String description;
  private String address;
  private StoreStatus storeStatus;
  private String storeLogo;    // 가게 배너 이미지
  private String userName; //사장님 이름-- StoreManagement엔티티에 아직 없음
  private LocalDateTime approvedDate; //승인 날짜
  //수정날짜로 받기

  /**
   * StoreManagement 엔티티에서 DTO로 데이터를 변환을 위한 생성자.
   */
  public AdminStoreListRequestDto(StoreManagement storeManagement) {
    this.storeId = storeManagement.getStoreId();
    this.storeName = storeManagement.getStoreName();
    this.contactNumber = storeManagement.getContactNumber();
    this.description = storeManagement.getDescription();
    this.address = storeManagement.getAddress();
    this.storeStatus = storeManagement.getStoreStatus();
    this.storeLogo = storeManagement.getStoreLogo();
    this.approvedDate = storeManagement.getApprovedDate();
  }
}
