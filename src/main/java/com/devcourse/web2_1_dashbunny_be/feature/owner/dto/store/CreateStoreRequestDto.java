package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.Categorys;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CategoryType;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import jakarta.validation.constraints.Size;
import java.util.List;

import lombok.*;


/**
 * 생성된 가게 정보를 넘겨주는 dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStoreRequestDto {
  private String storeName;             // 가게 이름
  private String contactNumber = null;         // 가게 연락처==
  private String address;               // 가게 위치
  private String description = null;           // 가게 소개--
  private Double latitude;              //위도
  private Double longitude;             //경도
  private String storeRegistrationDocs; // 등록 서류
  private StoreStatus storeStatus = StoreStatus.PENDING;      //가게 상태
  private String userPhone = null;             //사장님 이름

  @Size(max = 3, message = "카테고리는 최대 3개까지 선택할 수 있습니다.")
  private List<CategoryType> categories;

  /**
   * StoreManagement 객체 생성 메소드.
   */
  public StoreManagement toEntity(User user) {
    StoreManagement storeManagement = new StoreManagement();
    storeManagement.setStoreName(this.storeName);
    storeManagement.setContactNumber(this.contactNumber);
    storeManagement.setAddress(this.address);
    storeManagement.setStoreDescription(this.description);
    storeManagement.setStoreRegistrationDocs(this.storeRegistrationDocs);
    storeManagement.setLatitude(this.latitude);
    storeManagement.setLongitude(this.longitude);
    storeManagement.setStoreStatus(StoreStatus.PENDING);
    storeManagement.setUser(user);

    // CategoryType 리스트를 Categorys 리스트로 변환
    List<Categorys> categoryEntities = this.categories.stream()
            .map(categoryType -> {
              Categorys category = new Categorys();
              category.setCategoryType(categoryType);
              category.setStoreManagement(storeManagement); // 연관 설정
              return category;
            })
            .toList();

    storeManagement.setCategory(categoryEntities);
    return storeManagement;
  }
}