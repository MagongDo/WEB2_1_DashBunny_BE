package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import lombok.Data;

@Data
public class PaymentApproveResponseDto {
    private String paymentKey;
    private String orderId;
    private String status;   
    private Long amount;
    private String method;
}
