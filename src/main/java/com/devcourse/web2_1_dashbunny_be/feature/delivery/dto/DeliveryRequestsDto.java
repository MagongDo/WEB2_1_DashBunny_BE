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
	private Long orderId;           // 주문id
	private String deliveryAddress;
	private String deliveryDetailsAddress;
	private int preparationTime; // 요리 소요시간
	private String deliveryWorkerNote; // 라이더에게 전달할 메모
	private LocalDateTime orderDate; // 주문 받은 날짜
	private Long deliveryPrice;     // 배달 가격

}
