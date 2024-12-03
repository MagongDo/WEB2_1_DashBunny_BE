package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreFeedBackRepository extends JpaRepository<StoreFeedBack, Long> {
    StoreFeedBack findByStoreId(String storeId);
}
