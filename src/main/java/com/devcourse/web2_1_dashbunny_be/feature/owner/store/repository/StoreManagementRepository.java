package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.BasicInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 가게 레포지토리 클래스.
 */
@Repository
public interface StoreManagementRepository extends JpaRepository<StoreManagement, String> {

  /**
  *기본 정보 조회를 위한 쿼리.
  */
  @Query("""
       SELECT s.storeName AS storeName,
              s.contactNumber AS contactNumber,
              s.storeStatus AS storeStatus,
              s.address AS address,
              s.storeLogo AS storeLogo,
              s.storeBannerImage AS storeBannerImage,
              s.storeDescription AS storeDescription,
              (SELECT o.shortsUrl
               FROM StoreOperationInfo o
               WHERE o.store.storeId = s.storeId) AS shortsUrl
       FROM StoreManagement s
       WHERE s.storeId = :storeId
       """)
    BasicInfoProjection findBasicInfoByStoreId(@Param("storeId") String storeId);

  /**
   *
   */
  @Query("""
        SELECT o.operationId FROM StoreOperationInfo o
        WHERE o.store.storeId = :storeId
        """)
    StoreOperationInfo findStoreOperationInfoByStoreId(String storeId);
}
