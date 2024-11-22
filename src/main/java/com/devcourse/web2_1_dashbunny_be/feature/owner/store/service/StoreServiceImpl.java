package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreOperationInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 가게 관리를 위한 service class.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

  private final StoreManagementRepository storeManagementRepository;
  private final StoreOperationInfoRepository storeOperationInfoRepository;

  /**
   * 기본 정보를 반환 api service.
   */
  @Override
   public BasicInfoListResponseDto findBasicInfo(String storeId) {
      //가게의 기본정보 가져오와서 디티오 생성 . 스토어 에서 쇼츠 url 만 가져와야한다..

    return null;
   }

    @Override
    public void updateBasicInfo(UpdateBasicInfoRequestDto updateBasicInfo) {

    }

    @Override
    public OperationInfoListResponseDto findOperationInfo(String storeId) {
        return null;
    }

    @Override
    public void addOperationInfo(String storeId, CreateOperationInfoResponseDto operationInfo) {

    }

    @Override
    public void updatePauseTime(String storeId, UpdatePauseTimeRequestDto pauseTimeDto) {

    }

    @Override
    public void updateResumeTime(String storeId) {

    }
}
