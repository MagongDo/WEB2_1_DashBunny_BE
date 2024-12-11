package com.devcourse.web2_1_dashbunny_be.feature.delivery.repository;

import com.devcourse.web2_1_dashbunny_be.domain.delivery.DeliveryRequests;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.role.DeliveryRequestStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRequestsRepository extends JpaRepository<DeliveryRequests, Long> {

	@Query("SELECT d.status FROM DeliveryRequests d WHERE d.deliveryRequestId = :deliveryRequestId")
	DeliveryRequestStatus findStatusByDeliveryRequestId(@Param("deliveryRequestId") Long deliveryRequestId);

	// 비관적 락을 사용한 조회 메서드 추가
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT d FROM DeliveryRequests d WHERE d.deliveryRequestId = :deliveryRequestId")
	DeliveryRequests findByDeliveryRequestIdForUpdate(@Param("deliveryRequestId") Long deliveryRequestId);

}
