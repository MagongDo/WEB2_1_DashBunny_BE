
package com.devcourse.web2_1_dashbunny_be.feature.admin.store.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreApplicationType;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreIsApproved;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreClosureRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.StoreApplicationRepository;

import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.CreateStoreRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 가게 관리 service.
 */
//옮기기 서비스
@Service
@RequiredArgsConstructor
public class StoreManagementService {
  private final StoreManagementRepository storeManagementRepository;
  private final StoreApplicationRepository storeApplicationRepository;
  private final UserRepository userRepository;
  private final Validator validator;

  /**
   * 가게 등록 신청 메서드.
   */
  @Transactional
  public StoreManagement create(CreateStoreRequestDto storeCreateRequestDto) {
    User user = userRepository.findByPhone(storeCreateRequestDto.getUserPhone()).orElseThrow();
    // 가게 객체 생성
    StoreManagement savedStoreManagement = storeManagementRepository.save(storeCreateRequestDto.toEntity(user));

    //가게 신청 유형을 CREATE 으로 객체 생성
    storeApplicationRepository.save(
            StoreApplication.builder()
                    .storeManagement(savedStoreManagement)
                    .storeApplicationType(StoreApplicationType.CREATE)
                    .storeIsApproved(StoreIsApproved.WAIT)
                    .build()
    );
    return savedStoreManagement;
  }

  //가게 재등록 신청


  /**
   * 가게 재등록 신청 메서드.
   */
  @Transactional
  public StoreManagement reCreate(String storeId, CreateStoreRequestDto storeCreateRequestDto) {
    StoreManagement storeManagement = storeManagementRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store ID not found: " + storeId));

    // 이미 가게 번호가 생성되었으므로 StoreManagement 정보 업데이트
    storeManagement.setStoreName(storeCreateRequestDto.getStoreName());
    storeManagement.setContactNumber(storeCreateRequestDto.getContactNumber());
    storeManagement.setStoreDescription(storeCreateRequestDto.getDescription());
    storeManagement.setAddress(storeCreateRequestDto.getAddress());
//    storeManagement.setCategory1(storeCreateRequestDto.getCategory1());
//    storeManagement.setCategory2(storeCreateRequestDto.getCategory2());
//    storeManagement.setCategory3(storeCreateRequestDto.getCategory3());
    storeManagement.setStoreRegistrationDocs(storeCreateRequestDto.getStoreRegistrationDocs());
/*    storeManagement.setStoreBannerImage(storeCreateRequestDto.getStoreBannerImage());*/
    storeManagement.setStoreStatus(StoreStatus.PENDING); // 상태를 재등록 신청 중으로 변경

    // 업데이트된 StoreManagement 저장
    StoreManagement savedStoreManagement = storeManagementRepository.save(storeManagement);

    //가게 신청 유형을 CREATE 으로 객체 생성
    storeApplicationRepository.save(
            StoreApplication.builder()
                    .storeManagement(savedStoreManagement)
                    .storeApplicationType(StoreApplicationType.CREATE)
                    .storeIsApproved(StoreIsApproved.WAIT)
                    .build()
    );
    return savedStoreManagement;
  }
//, StoreClosureRequestDto closureRequestDto

  /**
   * 가게 폐업 신청 메서드.
   */
  @Transactional
  public StoreManagement close(String storeId) {
    StoreManagement storeManagement = storeManagementRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store ID not found: " + storeId));


    if (storeManagement.getStoreStatus() == StoreStatus.TEMP_CLOSE) {
      storeManagement.setStoreStatus(StoreStatus.CLOSURE_PENDING);

      // 가게 신청 유형을 CLOSURE 으로 객체 생성
      storeApplicationRepository.save(
              StoreApplication.builder()
                      .storeManagement(storeManagement)
                      .storeApplicationType(StoreApplicationType.CLOSURE)
                      .storeIsApproved(StoreIsApproved.WAIT)
                      .build()
      );

      return storeManagementRepository.save(storeManagement);
    }

    //가게 상태가 TEMP_CLOSE 가 아닐시 에러 반환
    throw new IllegalStateException("Cannot apply for closure: Store is not in TEMP_CLOSE status.");
  }

  /**
   * 기본 정보 - 스토어 로고 이미지 변경.
   */
  public void updateLogoImage(String storeId, String fileUrl) {
    StoreManagement store = validator.validateStoreId(storeId);
    store.setStoreLogo(fileUrl);
    storeManagementRepository.save(store);
  }

  /**
   * 기본 정보 - 스토어 배너 이미지 변경.
   */
  public void updateBannerImage(String storeId, String fileUrl) {
    StoreManagement store = validator.validateStoreId(storeId);
    store.setStoreBannerImage(fileUrl);
    storeManagementRepository.save(store);
  }
  //storeBannerImage

}

