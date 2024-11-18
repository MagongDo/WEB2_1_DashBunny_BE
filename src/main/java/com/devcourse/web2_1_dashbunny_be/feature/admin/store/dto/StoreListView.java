package com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto;


import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;

//가게 번호, 가게배너사진, 가게 이름,사장님 이름, 전화번호, 가게 소개, 위치 ,등록상태, 날짜
public class StoreListView {
    private String storeId;
    private String storeName;
    private String contactNumber;
    private String description;
    private String address;
    private StoreStatus storeStatus;
    //수정날짜로 받기

}
