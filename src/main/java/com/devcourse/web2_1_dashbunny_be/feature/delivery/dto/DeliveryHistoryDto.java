package com.devcourse.web2_1_dashbunny_be.feature.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DeliveryHistoryDto {
	private Long userId;
	private Long deliveryRequestId;
	private LocalDateTime registeredAt;
	private LocalDateTime completedAt;
	private Double fee;
	private String uniqueCode;
	private Double distance;
	private String status;
	private String deliveryCompletePhoto;
	private String deliveryIssues;
}
