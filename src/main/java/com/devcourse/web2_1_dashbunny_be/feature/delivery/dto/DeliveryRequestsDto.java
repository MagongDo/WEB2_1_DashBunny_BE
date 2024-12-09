package com.devcourse.web2_1_dashbunny_be.feature.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DeliveryRequestsDto {
	private Long deliveryRequestId;
	private String storeId;
	private String storeName;
	private String deliveryAddress;
	private String deliveryDetailsAddress;
	private String driverRequest;
	private LocalDateTime createdDate; // 배달 요청 일시
}
