package com.devcourse.web2_1_dashbunny_be.feature.delivery.repository;

import com.devcourse.web2_1_dashbunny_be.domain.delivery.DeliveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {
}
