package com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 가게 단일 정보 조회 데이터를 넘겨주는 DTO.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminStoreResponseDto {
  private String storeId;
  private  String storeName;          // 가게 이름
  private String userPhone;      // 가게 연락처
  private String address;            // 가게 위치
  private String description;        // 가게 소개
  private List<String> categories;   // 카테고리 리스트로 변경
  private Double latitude;  //위도
  private Double longitude; //경도
  private String storeRegistrationDocs; // 등록 서류
  private String storeBannerImage;    // 가게 배너 이미지
  private StoreStatus storeStatus; //가게 상태
  private String userName; //사장님 이름

  /**
   * StoreManagement 엔티티에서 DTO로 데이터를 변환을 위한 생성자.
   */
  public AdminStoreResponseDto(StoreManagement storeManagement) {
    this.storeId = storeManagement.getStoreId();
    this.storeName = storeManagement.getStoreName();
    this.userPhone = storeManagement.getUser().getPhone();
    this.address = storeManagement.getAddress();
    this.description = storeManagement.getStoreDescription();
    this.categories = storeManagement.getCategory().stream() // 가게 카테고리 리스트 생성
            .map(category -> category.getCategoryType().name()) // Enum 값을 문자열로 변환
            .collect(Collectors.toList());
    this.storeRegistrationDocs = storeManagement.getStoreRegistrationDocs();
    this.storeBannerImage = storeManagement.getStoreBannerImage();
    this.storeStatus = storeManagement.getStoreStatus();
    this.userName = storeManagement.getUser().getName();

  }
}
