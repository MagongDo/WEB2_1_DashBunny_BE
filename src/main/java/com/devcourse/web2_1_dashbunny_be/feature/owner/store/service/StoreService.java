package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;


import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.*;

/**
 * 가게 관리를 위한 service interface.
 */
public interface StoreService {

  /**
   * 기본 정보를 반환 api service.
   */
  BasicInfoListResponseDto findBasicInfo(String storeId);

  /**
   * 기본 정보 수정을 위한 api service.
   */
  void updateBasicInfo(UpdateBasicInfoRequestDto updateBasicInfo);

    OperationInfoListResponseDto findOperationInfo(String storeId);

    void addOperationInfo(String storeId, CreateOperationInfoResponseDto operationInfo);

    void updatePauseTime(String storeId, UpdatePauseTimeRequestDto pauseTimeDto);

    void updateResumeTime(String storeId);
}
