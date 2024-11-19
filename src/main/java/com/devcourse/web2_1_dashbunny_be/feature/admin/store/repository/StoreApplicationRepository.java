package com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreApplicationRepository extends JpaRepository<StoreApplication, Long> {
   // StoreApplication findByStoreId(String storeId);

    @Query("SELECT sa FROM StoreApplication sa WHERE sa.storeManagement.storeId = :storeId")
    StoreApplication findByStoreId(String storeId);

    //Optional<StoreApplication> findByStoreId(@Param("storeId") String storeId);
}
