package com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.search;

import com.devcourse.web2_1_dashbunny_be.domain.owner.QStoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreListRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * StoreSearch 인터페이스를 구현하는 클래스.
 */
public class StoreSearchImpl extends QuerydslRepositorySupport implements StoreSearch {

    /**
     * 생성자에서 부모 클래스의 생성자 호출 (StoreManagement 클래스에 대한 Querydsl 지원).
     */
    public StoreSearchImpl() {
        super(StoreManagement.class);
    }

    @Override
    public Page<AdminStoreListRequestDto> storeSearch(Pageable pageable) {
        QStoreManagement store = QStoreManagement.storeManagement;



        return null;
    }
}
