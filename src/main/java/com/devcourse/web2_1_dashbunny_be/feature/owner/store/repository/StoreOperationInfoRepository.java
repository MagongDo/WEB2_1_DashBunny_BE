package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 가게 운영 정보 repository.
 */
@Repository
public interface StoreOperationInfoRepository extends JpaRepository<StoreOperationInfo, Long> {
}
