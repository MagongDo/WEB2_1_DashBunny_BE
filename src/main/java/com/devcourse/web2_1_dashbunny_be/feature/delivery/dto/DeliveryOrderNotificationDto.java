package com.devcourse.web2_1_dashbunny_be.feature.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryOrderNotificationDto { // 배달원에게 가는 배달알림 dto

	private Long deliveryRequestId;
	private String storeId;
	private String storeName;
	private String storeAddress;
	private int preparationTime; // 요리 소요시간
	private double distance; // 배달 거리
	private int deliveryPrice; // 배달료
	private String deliveryAddress;
	private String deliveryDetailsAddress;
//	private String uniqueCode; // 검색용 코드 (숫자 영문 8자리 ex) 5D7Q3Z1D )
	private String deliveryWorkerNote; // 라이더에게 전달할 메모

	/*
		배달요청 ID
		가게 ID
		가게 이름
		가게 주소
		조리시간
		거리
		배달료
		배달 주소
		배달 주소 디테일
		고유번호
		기사 요청사항
	*/

}
