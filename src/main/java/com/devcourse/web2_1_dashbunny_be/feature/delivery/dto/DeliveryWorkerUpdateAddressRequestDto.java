package com.devcourse.web2_1_dashbunny_be.feature.delivery.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeliveryWorkerUpdateAddressRequestDto {

	@NotBlank(message = "deliveryWorkerId는 필수입니다.")
	private Long userId;
	@NotBlank(message = "사용자의 주소는 필수입니다.")
	private String address;

}
