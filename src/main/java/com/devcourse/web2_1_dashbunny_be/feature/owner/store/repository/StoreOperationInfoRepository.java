package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 가게 운영 정보 repository.
 */
public interface StoreOperationInfoRepository extends JpaRepository<StoreOperationInfo, Long> {
   StoreOperationInfo findByShortsUrl;
}
