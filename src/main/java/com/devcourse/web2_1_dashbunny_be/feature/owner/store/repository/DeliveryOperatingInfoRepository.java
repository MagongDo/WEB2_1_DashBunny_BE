package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryOperatingInfoRepository extends JpaRepository<DeliveryOperatingInfo, Long> {
    Optional<DeliveryOperatingInfo> findFirstByOrderByIdDesc();
}


