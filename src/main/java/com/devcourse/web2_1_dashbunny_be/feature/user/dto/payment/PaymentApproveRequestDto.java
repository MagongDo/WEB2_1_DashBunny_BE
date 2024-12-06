package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import lombok.Data;

@Data
public class PaymentApproveRequestDto {
    private String paymentKey;
    private String orderId;
    private Long amount;
}
