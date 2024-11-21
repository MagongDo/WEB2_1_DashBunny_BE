package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuManagement, Long> {
    @Query("SELECT m FROM MenuManagement m WHERE m.storeId = :storeId")
    List<MenuManagement> findAllByStoreId(@Param("storeId") String storeId);
}
