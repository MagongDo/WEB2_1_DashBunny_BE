package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreOperationInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 가게 관리를 위한 service class.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

  private final StoreManagementRepository storeManagementRepository;
  private final StoreOperationInfoRepository storeOperationInfoRepository;
  private final Validator validator;

  /**
   * 기본 정보를 반환 api service.
   */
  @Override
  public BasicInfoProjection findBasicInfo(String storeId) {
    return validator.validateBasicStoreId(storeId);
  }

  /**
  *기본 정보 수정을 위한 api service.
  */
  @Override
  public void updateBasicInfo(String storeId, UpdateBasicInfoRequestDto updateBasicInfo) {
    StoreManagement store = validator.validateStoreId(storeId);

    if (updateBasicInfo.getContactNumber() != null) {
      store.setContactNumber(updateBasicInfo.getContactNumber());
    }
    if (updateBasicInfo.getStoreLogo() != null) {
      store.setStoreLogo(updateBasicInfo.getStoreLogo());
    }
    if (updateBasicInfo.getStoreBannerImage() != null) {
      store.setStoreBannerImage(updateBasicInfo.getStoreBannerImage());
    }
    if (updateBasicInfo.getStoreDescription() != null) {
      store.setStoreDescription(updateBasicInfo.getStoreDescription());
    }

    StoreOperationInfo operationInfo = store.getStoreOperation();
    if (operationInfo != null && updateBasicInfo.getShortsUrl() != null) {
      operationInfo.setShortsUrl(updateBasicInfo.getShortsUrl());
    }

    storeManagementRepository.save(store);
  }

  /**
  * 가게 운영 정보 조회를 위한 api service.
   * 운영 정보가 없을 때, 기본 정보를 기반으로 새로운 운영 정보를 생성하고 반환하도록 코드를 작성.
   */
  @Override
  public OperationInfoListResponseDto findOperationInfo(String storeId) {
    StoreManagement store = validator.validateStoreId(storeId);
    log.info("Find operation info for store {}", storeId);
    StoreOperationInfo operationInfo = store.getStoreOperation();

    if (operationInfo == null) {
      log.info("널 {}", storeId);
      operationInfo = new StoreOperationInfo();
      operationInfo.setStore(store);
      operationInfo.setShortsUrl("쇼츠 url 입력");
      store.setStoreOperation(operationInfo);
      operationInfo.setPaused(Boolean.FALSE);
      operationInfo.setPauseStartTime("시작 시간");
      operationInfo.setPauseEndTime("종료 시간");

      store.setStoreOperation(operationInfo);
      storeOperationInfoRepository.save(operationInfo);
      log.info("저장완료");
      operationInfo = validator.validateOperationStoreId(operationInfo.getOperationId());
    }
    log.info("생성된 운영정보 아이디 {}", operationInfo.getOperationId());

    return OperationInfoListResponseDto.fromEntity(operationInfo);
  }

  @Override
  public void addOperationInfo(String storeId, CreateOperationInfoResponseDto operationInfo) {
    StoreManagement store = validator.validateStoreId(storeId);
    StoreOperationInfo storeOperationInfo = store.getStoreOperation();

    if (operationInfo.getOpeningHours() != null) {
      storeOperationInfo.setOpeningHours(operationInfo.getOpeningHours());
    }
    if (operationInfo.getBreakTime() != null) {
      storeOperationInfo.setBreakTime(operationInfo.getBreakTime());
    }
    if (operationInfo.getHolidayDays() != null) {
      storeOperationInfo.setHolidayDays(operationInfo.getHolidayDays());
    }
    if (operationInfo.getHolidayNotice() != null) {
      storeOperationInfo.setHolidayNotice(operationInfo.getHolidayNotice());
    }

    storeOperationInfoRepository.save(storeOperationInfo);
  }

  /**
   *가게 운영 일시정지를 위한 api service.
   */
  @Override
  public void updatePauseTime(String storeId, UpdatePauseTimeRequestDto pauseTimeDto) {
    StoreManagement store = validator.validateStoreId(storeId);
    StoreOperationInfo storeOperationInfo =  store.getStoreOperation();

    storeOperationInfo.setPauseStartTime(pauseTimeDto.getPauseStartTime());
    log.info("시작 시간 {}", pauseTimeDto.getPauseStartTime());
    storeOperationInfo.setPauseEndTime(pauseTimeDto.getPauseEndTime());
    log.info("종료 시간 ()", pauseTimeDto.getPauseEndTime());
    storeOperationInfo.setPaused(false);

    storeOperationInfoRepository.save(storeOperationInfo);
  }

  /**
   *가게 운영 재시작 위한 api service.
  */
  @Override
  public void updateResumeTime(String storeId) {
    StoreManagement store = validator.validateStoreId(storeId);

    StoreOperationInfo storeOperationInfo =  store.getStoreOperation();
    storeOperationInfo.setPaused(true);

    storeOperationInfoRepository.save(storeOperationInfo);
    }
}
