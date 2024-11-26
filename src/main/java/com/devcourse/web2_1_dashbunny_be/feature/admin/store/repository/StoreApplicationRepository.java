package com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 가게 생성 repository.
 */
public interface StoreApplicationRepository extends JpaRepository<StoreApplication, Long> {

  /**
   * 가게 번호로 조회.
   */
  @Query("SELECT sa FROM StoreApplication sa WHERE sa.storeManagement.storeId = :storeId")
  StoreApplication findByStoreId(String storeId);

  /**
   * 등록을 신청한 대기중인 가게 번호 조회.
   */
  @Query("SELECT sa FROM StoreApplication sa WHERE sa.storeManagement.storeId = :storeId AND sa.storeApplicationType='CREATE' AND sa.storeIsApproved = 'WAIT'")
  StoreApplication findByStoreIdAndCreateAndWait(@Param("storeId") String storeId);

  /**
   * 폐업신청을 한 가게 번호 조회.
   */
  @Query("SELECT sa FROM StoreApplication sa WHERE sa.storeManagement.storeId = :storeId AND sa.storeApplicationType = 'CLOSURE'")
  StoreApplication findByStoreIdAndClosure(@Param("storeId") String storeId);
}
