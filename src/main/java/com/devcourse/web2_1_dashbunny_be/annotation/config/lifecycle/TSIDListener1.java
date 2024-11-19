package com.devcourse.web2_1_dashbunny_be.annotation.config.lifecycle;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import jakarta.persistence.PrePersist;

public class TSIDListener1 {
    @PrePersist
    public void generateId(Object entity) {
        if (entity instanceof StoreManagement) {
            StoreManagement store = (StoreManagement) entity;
            if (store.getStoreId() == null || store.getStoreId().isEmpty()) {
                store.setStoreId(generateTSID());
            }
        }
    }

    private String generateTSID() {
        // TSID 생성 로직: 현재 타임스탬프 기반 예시
        return "TSID-" + System.currentTimeMillis();
    }
}
