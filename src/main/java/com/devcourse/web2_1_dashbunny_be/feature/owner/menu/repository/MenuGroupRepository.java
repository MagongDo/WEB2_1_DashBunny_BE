package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository;

import com.amazonaws.services.s3.model.Owner;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메뉴 그룹 레포지토리 인터페이스.
 */
@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {

  /**
  * 동일한 storeId를 가진 그룹 List 반환.
  */
  @Query("""
          SELECT m FROM MenuGroup m WHERE m.storeId = :storeId
         """)
  List<MenuGroup> findByStoreId(String storeId);
}
