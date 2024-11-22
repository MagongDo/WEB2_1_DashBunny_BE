package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 가게 레포지토리 클래스.
 */
@Repository
public interface StoreManagementRepository extends JpaRepository<StoreManagement, String> {
}
