package com.devcourse.web2_1_dashbunny_be.feature.user.dto.Refund;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RefundResponseDto {
  private String refundId;
  private String status;
  private Long amount;
  private String reason;
  private String createdAt;
}
