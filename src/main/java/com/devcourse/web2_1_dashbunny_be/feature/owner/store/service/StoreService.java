package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;


import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.BasicInfoListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.UpdateBasicInfoRequestDto;

public interface StoreService {
    BasicInfoListResponseDto findBasicInfo(String storeId);

    void updateBasicInfo(UpdateBasicInfoRequestDto updateBasicInfo);
}
