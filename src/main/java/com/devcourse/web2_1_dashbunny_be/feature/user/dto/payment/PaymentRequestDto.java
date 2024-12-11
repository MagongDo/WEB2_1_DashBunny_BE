package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private String method;
    @JsonProperty("orderId")
    private String orderId; // 고유한 주문 ID

    @JsonProperty("orderName")
    private String orderName; // 주문 이름

    @JsonProperty("amount")
    private Long amount; // 결제 금액

    // customerName이 필수가 아니라면 포함하지 않거나, null 대신 빈 문자열로 설정
    @JsonProperty("customerName")
    private String customerName; // 고객 이름 (필수 아님)

    @JsonProperty("successUrl")
    private String successUrl; // 결제 성공 시 리다이렉트 URL

    @JsonProperty("failUrl")
    private String failUrl; // 결제 실패 시 리다이렉트 URL

    private String redirectUrl;
}