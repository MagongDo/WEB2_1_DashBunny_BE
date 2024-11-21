package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.*;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Override
    public BasicInfoListResponseDto findBasicInfo(String storeId) {
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
