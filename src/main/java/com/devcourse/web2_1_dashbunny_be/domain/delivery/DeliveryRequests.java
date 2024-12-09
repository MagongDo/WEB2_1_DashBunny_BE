package com.devcourse.web2_1_dashbunny_be.domain.delivery;

import com.devcourse.web2_1_dashbunny_be.domain.delivery.role.DeliveryRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "delivery_requests")
@Entity
@Getter
@Builder(toBuilder = true)
@ToString
@EntityListeners(AuditingEntityListener.class) // Date를 등록, 수정 일시 자동 반영 중요!!
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequests {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deliveryRequestId;

	@Column(nullable = false)
	private String storeId; // 가게 ID

	private Long orderId;	// 주문 ID

	@Column(nullable = false, length = 255)
	private String deliveryAddress; // 배달 주소
	
	private String deliveryDetailsAddress; // 배달 상세 주소(102동 205호 등등)

	private int preparationTime; // 요리 소요시간

	@Column(length = 255)
	private String deliveryWorkerNote; // 기사 요청사항

	private Double distance; // 거리

	private Long deliveryPrice;     // 배달 가격

	private LocalDateTime orderDate; // 주문 받은 날짜

	@CreatedDate
	private LocalDateTime createdDate;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	@Column(nullable = false, length = 50)
	private DeliveryRequestStatus status = DeliveryRequestStatus.REQUESTED; // 음식 상태

	@Version
	private Integer version; // 낙관적 락
}
