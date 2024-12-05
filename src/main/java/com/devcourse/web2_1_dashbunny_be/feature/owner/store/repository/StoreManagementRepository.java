package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.BasicInfoProjection;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.StoreManagementListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 가게 레포지토리 클래스.
 */
@Repository
public interface StoreManagementRepository extends JpaRepository<StoreManagement, String> {

  /**
  * 기본 정보 조회를 위한 쿼리.
  */
  @Query(value = """
         SELECT s.store_name AS storeName,
                s.contact_number AS contactNumber,
                s.store_status AS storeStatus,
                s.address AS address,
                s.store_logo AS storeLogo,
                s.store_banner_image AS storeBannerImage,
                s.store_description AS storeDescription,
                (SELECT GROUP_CONCAT(CONCAT(o.menu_name, ':', o.shorts_url))
                 FROM store_operation_info o
                 WHERE o.store_id = s.store_id) AS shortsInfo,
                (SELECT GROUP_CONCAT(m.menu_name)
                 FROM menu_management m
                 WHERE m.store_id = s.store_id) AS menuList
         FROM store_management s
         WHERE s.store_id = :storeId
                     """, nativeQuery = true)
  BasicInfoProjection findBasicInfoByStoreId(@Param("storeId") String storeId);

  /**
   * 운영 정보 조회를 위한 쿼리.
   */
  @Query("""
        SELECT o.operationId FROM StoreOperationInfo o
        WHERE o.store.storeId = :storeId
        """)
    StoreOperationInfo findStoreOperationInfoByStoreId(String storeId);


  @Query("""
      SELECT s.storeId AS storeId,
             s.storeName AS storeName,
             s.storeStatus AS storeStatus
      FROM StoreManagement s
      WHERE s.user = :userId
        """)
  List<StoreManagementListDto> findByAll(@Param("userId") User user);
  /**
   * 관리자 - 특정 상태의 가게를 페이징 처리하여 조회.
   */
  Page<StoreManagement> findByStoreStatus(StoreStatus storeStatus, Pageable pageable);

  /**
   * 관리자 - 여러 상태를 포함한 가게 목록 조회.
   */
  Page<StoreManagement> findByStoreStatusIn(List<StoreStatus> storeStatuses, Pageable pageable);

  StoreManagement findByStoreId(String storeId);

}
