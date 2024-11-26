package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;

import java.util.ArrayList;
import java.util.List;

class MockStoreData {
    static List<StoreManagement> getMockStores() {
        List<StoreManagement> stores = new ArrayList<>();

        // 배달 가능한 가게
        for (int i = 1; i <= 8; i++) {
            StoreManagement store = new StoreManagement();
            store.setStoreId("store" + i);
            store.setStoreName("KOREAN Restaurant " + i);
            store.setLatitude(37.5665 + i * 0.001); // 사용자 근처
            store.setLongitude(126.9780 + i * 0.001);
            stores.add(store);
        }

        // 배달 불가능한 가게
        for (int i = 9; i <= 10; i++) {
            StoreManagement store = new StoreManagement();
            store.setStoreId("store" + i);
            store.setStoreName("Faraway Restaurant " + i);
            store.setLatitude(40.0); // 먼 위치
            store.setLongitude(130.0);
            stores.add(store);
        }

        return stores;
    }
}
