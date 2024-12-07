package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentApproveRequestDto {
    private String paymentKey;
    private String orderId;
    private Long amount;
}
