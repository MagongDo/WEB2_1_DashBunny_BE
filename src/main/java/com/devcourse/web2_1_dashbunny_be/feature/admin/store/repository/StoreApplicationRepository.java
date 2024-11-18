package com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreApplicationRepository extends JpaRepository<StoreApplication, Long> {
    StoreApplication findByRefereceId(String storeId);
}
