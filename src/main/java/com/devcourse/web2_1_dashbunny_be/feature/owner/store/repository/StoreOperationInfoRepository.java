package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 가게 운영 정보 repository.
 */
@Repository
public interface StoreOperationInfoRepository extends JpaRepository<StoreOperationInfo, Long> {

    // 특정 StoreId로 StoreOperationInfo를 조회하는 쿼리 메서드
    StoreOperationInfo findByStore(StoreManagement store);

}
