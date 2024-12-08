package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import lombok.Data;

@Data
public class PaymentFailResponseDto {
    private String orderId;
    private String paymentKey;
    private String code;
    private String message;
}
