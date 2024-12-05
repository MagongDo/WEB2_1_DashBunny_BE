package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentWebhookResponseDto {
  private String eventType;
  private String paymentKey;
  private String orderId;
  private String status;
  private String transactionType;
  private Long amount;
  private String approvedAt;
  private String currency;
  private String reason;
  private String reasonCode;
}