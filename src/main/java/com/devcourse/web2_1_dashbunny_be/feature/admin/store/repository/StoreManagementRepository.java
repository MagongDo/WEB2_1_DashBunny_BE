package com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 가게 관리 repository.
 */
public interface StoreManagementRepository extends JpaRepository<StoreManagement, String> {

  /**
   * 관리자 - 특정 상태의 가게를 페이징 처리하여 조회.
   */
  Page<StoreManagement> findByStoreStatus(StoreStatus storeStatus, Pageable pageable);

  /**
   * 관리자 - 여러 상태를 포함한 가게 목록 조회.
   */
  Page<StoreManagement> findByStoreStatusIn(List<StoreStatus> storeStatuses, Pageable pageable);

}
