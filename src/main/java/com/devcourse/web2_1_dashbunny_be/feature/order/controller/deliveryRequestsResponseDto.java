package com.devcourse.web2_1_dashbunny_be.feature.order.controller;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class deliveryRequestsResponseDto {

    private String storeId;         //가게id
    private Long orderId;           // 주문id
    private String address;         //주소
    private String detailAddress;   // 상세 주소
    private int preparationTime;
    private LocalDateTime orderDate; // 주문 날짜
    private String riderNote;        // 라이더에게 전달할 메모
    private Long deliveryPrice;     // 배달 가격


}
