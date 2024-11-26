package com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreManagementRepository extends JpaRepository<StoreManagement, String> {
}
