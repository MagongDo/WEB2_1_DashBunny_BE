package com.devcourse.web2_1_dashbunny_be.feature.user.dto.Refund;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RefundRequestDto {
  private Long cancelAmount;
  private String cancelReason;
}
