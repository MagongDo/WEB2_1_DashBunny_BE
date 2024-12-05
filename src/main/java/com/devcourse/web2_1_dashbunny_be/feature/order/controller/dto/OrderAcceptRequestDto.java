package com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto;

import lombok.Getter;

@Getter
public class OrderAcceptRequestDto {
  private String storeId; // 가게 ID
  private Long orderId;
  private int preparationTime; // 준비 시간 등 추가 정보
    //조리 예상 시간
}
