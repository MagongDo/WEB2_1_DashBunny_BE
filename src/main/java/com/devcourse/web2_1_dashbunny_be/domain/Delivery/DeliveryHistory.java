package com.devcourse.web2_1_dashbunny_be.domain.Delivery;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "delivery_history")
@Entity
@Getter
@Builder(toBuilder = true)
@ToString
@EntityListeners(AuditingEntityListener.class) // Date를 등록, 수정 일시 자동 반영 중요!!
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId; // 배달원 ID

	private Long deliveryRequestId; // 배달요청 ID

	@CreatedDate
	private LocalDateTime registeredAt; // 배달승낙일시

	private LocalDateTime completedAt; // 배달완료일시

	@Column(nullable = false)
	private Double fee; // 배달료

	@Column(nullable = false, length = 255)
	private String uniqueCode; // 검색용 코드 (숫자 영문 8자리)

	@Column(nullable = false)
	private Double distance; // 배달 거리

	@Column(nullable = false, length = 50)
	private String status; // 배달 상태

	private String deliveryCompletePhoto; // 배달 완료 사진

	private String deliveryIssues; // 배달 상태에 따른 이슈 메모
}
