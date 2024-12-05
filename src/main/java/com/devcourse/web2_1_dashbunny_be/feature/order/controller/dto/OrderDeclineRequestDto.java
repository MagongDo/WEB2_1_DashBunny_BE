package com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto;

import com.devcourse.web2_1_dashbunny_be.domain.user.role.DeclineReasonType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDeclineRequestDto {
  private String storeId;
  private Long orderId;
  private DeclineReasonType declineReasonType;
}
