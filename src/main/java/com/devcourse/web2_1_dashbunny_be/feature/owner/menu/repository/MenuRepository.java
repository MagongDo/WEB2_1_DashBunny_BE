package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * 메뉴 그룹 레포지토리 인터페이스.
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuManagement, Long> {

  /**
  * storeId를 기준으로 엔티티를 찾아냔다..
  */
  @Query("SELECT m FROM MenuManagement m WHERE m.storeId = :storeId")
 List<MenuManagement> findAllByStoreId(@Param("storeId") String storeId);
}
