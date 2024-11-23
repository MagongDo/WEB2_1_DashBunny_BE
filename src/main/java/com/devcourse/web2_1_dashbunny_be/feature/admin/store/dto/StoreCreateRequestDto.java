package com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto;


import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 생성된 가게 정보를 넘겨주는 dto.
 */
@Getter
@Builder
@AllArgsConstructor
public class StoreCreateRequestDto {
  private String storeName;          // 가게 이름
  private String contactNumber;      // 가게 연락처
  private String address;            // 가게 위치
  private String description;        // 가게 소개
  private String category1;          // 대표 카테고리
  private String category2;          // 추가 카테고리 1
  private String category3;          // 추가 카테고리 2
  private String storeRegistrationDocs; // 등록 서류
  private String storeLogo;          // 가게 매장 로고
  private String storeBannerImage;    // 가게 배너 이미지
  private StoreStatus storeStatus;
  //private String userName; //사장님 이름-- StoreManagement엔티티에 아직 없음

  /**
   * StoreManagement 객체 생성 메소드.
   */
  public StoreManagement toEntity() {
    StoreManagement storeManagement = new StoreManagement();
    storeManagement.setStoreName(this.storeName);
    storeManagement.setContactNumber(this.contactNumber);
    storeManagement.setAddress(this.address);
    storeManagement.setDescription(this.description);
//    storeManagement.setCategory1(this.category1);
//    storeManagement.setCategory2(this.category2);
//    storeManagement.setCategory3(this.category3);
    storeManagement.setStoreRegistrationDocs(this.storeRegistrationDocs);
    storeManagement.setStoreLogo(this.storeLogo);
    storeManagement.setStoreBannerImage(this.storeBannerImage);
    storeManagement.setStoreStatus(StoreStatus.PENDING);
    return storeManagement;
  }

/*    public StoreManagement toEntity() {
        return StoreManagement.builder()
                .storeName(storeName)
                .contactNumber(contactNumber)
                .address(address)
                .description(description)
                .category1(category1)
                .category2(category2)
                .category3(category3)
                .storeRegistrationDocs(storeRegistrationDocs)
                .storeLogo(storeLogo)
                .storeBannerImage(storeBannerImage)
                .storeStatus(storeStatus)
                //사장님 이름
                .build();

    }*/
}