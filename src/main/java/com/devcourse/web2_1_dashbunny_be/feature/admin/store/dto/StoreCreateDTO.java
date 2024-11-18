package com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto;


import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;

public record StoreCreateDTO(
        String storeName,          // 가게 이름
        String contactNumber,      // 가게 연락처
        String address,            // 가게 위치
        String description,        // 가게 소개
        String category1,          // 대표 카테고리
        String category2,          // 추가 카테고리 1
        String category3,          // 추가 카테고리 2
        String storeRegistrationDocs // 등록 서류
) {
    public StoreManagement toEntity() {
        StoreManagement storeManagement = new StoreManagement();
        storeManagement.setStoreName(this.storeName);
        storeManagement.setContactNumber(this.contactNumber);
        storeManagement.setAddress(this.address);
        storeManagement.setDescription(this.description);
        storeManagement.setCategory1(this.category1);
        storeManagement.setCategory2(this.category2);
        storeManagement.setCategory3(this.category3);
        storeManagement.setStoreRegistrationDocs(this.storeRegistrationDocs);
        storeManagement.setStoreStatus(StoreStatus.PENDING);
        return storeManagement;
    }
}
