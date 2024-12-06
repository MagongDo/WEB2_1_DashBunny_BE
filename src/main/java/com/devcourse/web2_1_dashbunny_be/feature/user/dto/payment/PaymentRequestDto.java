package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private String orderId;
    private String orderName;
    private Long amount;
    private String customerName;
    private String successUrl;
    private String failUrl;
}