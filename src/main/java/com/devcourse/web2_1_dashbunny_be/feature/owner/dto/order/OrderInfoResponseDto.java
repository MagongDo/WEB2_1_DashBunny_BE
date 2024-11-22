package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoResponseDto {
    private String isTakeout; // 포장 여부 (On/Off)
    private int takeoutDiscount; // 포장 할인 금액
    private int minOrderAmount; // 최소 주문 금액
    private int deliveryTip; // 기본 배달 팁
}