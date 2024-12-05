package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;




@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
  private String method;       // 결제 수단 (예: CARD)
  private String orderId;      // 주문 ID
  private String orderName;    // 주문 이름
  private Long amount;         // 결제 금액
  private String successUrl;   // 결제 성공 리다이렉트 URL
  private String failUrl;      // 결제 실패 리다이렉트 URL
}