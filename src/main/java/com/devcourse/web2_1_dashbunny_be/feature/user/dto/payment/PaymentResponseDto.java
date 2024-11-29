package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
  private String paymentId;      // Toss 결제 고유 ID
  private String orderName;
  private Long amount;
  private String status;
  private String storeName;
  private String successUrl;
  private String failUrl;
  private String clientKey;      // 프론트엔드에서 필요
  private String redirectUrl;    // 클라이언트가 결제 페이지로 리디렉션할 URL
 }
