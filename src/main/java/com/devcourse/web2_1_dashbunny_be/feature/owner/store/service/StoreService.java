package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;


import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.*;

public interface StoreService {
    BasicInfoListResponseDto findBasicInfo(String storeId);

    void updateBasicInfo(UpdateBasicInfoRequestDto updateBasicInfo);

    OperationInfoListResponseDto findOperationInfo(String storeId);

    void addOperationInfo(String storeId, CreateOperationInfoResponseDto operationInfo);

    void updatePauseTime(String storeId, UpdatePauseTimeRequestDto pauseTimeDto);

    void updateResumeTime(String storeId);
}
