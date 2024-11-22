package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderInfoRequestDto {
    private int takeoutDiscount; // 수정할 포장 할인 금액
    private int minOrderAmount; // 수정할 최소 주문 금액
    private int deliveryTip; // 수정할 기본 배달 팁
}