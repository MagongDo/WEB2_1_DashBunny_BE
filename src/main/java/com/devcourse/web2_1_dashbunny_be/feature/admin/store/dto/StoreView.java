package com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;

public class StoreView {
    private String storeId;
    private  String storeName;          // 가게 이름
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

    public StoreView(StoreManagement storeManagement) {
        this.storeId = storeManagement.getStoreId();
        this.storeName = storeManagement.getStoreName();
        this.contactNumber = storeManagement.getContactNumber();
        this.address = storeManagement.getAddress();
        this.description = storeManagement.getDescription();
        this.category1 = storeManagement.getCategory1();
        this.category2 = storeManagement.getCategory2();
        this.category3 = storeManagement.getCategory3();
        this.storeRegistrationDocs = storeManagement.getStoreRegistrationDocs();
        this.storeLogo = storeManagement.getStoreLogo();
        this.storeBannerImage = storeManagement.getStoreBannerImage();
        this.storeStatus = storeManagement.getStoreStatus();

    }
}
