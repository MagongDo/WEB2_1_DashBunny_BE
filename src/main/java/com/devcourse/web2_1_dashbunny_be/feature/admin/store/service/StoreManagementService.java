package com.devcourse.web2_1_dashbunny_be.feature.admin.store.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreApplicationType;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreIsApproved;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreClosureRequest;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreCreateRequest;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreListView;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreView;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.StoreApplicationRepository;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.StoreManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreManagementService {
    private final StoreManagementRepository storeManagementRepository;
    private final StoreApplicationRepository storeApplicationRepository;

    // 가게 등록 신청
    @Transactional
    public StoreManagement create(StoreCreateRequest storeCreateRequest) {
        StoreManagement savedStoreManagement = storeManagementRepository.save(storeCreateRequest.toEntity());
        storeApplicationRepository.save(
                StoreApplication.builder()
                        .storeManagement(savedStoreManagement)
                        .storeApplicationType(StoreApplicationType.CREATE)
                        .storeIsApproved(StoreIsApproved.WAIT)
                        .build()
        );
        return savedStoreManagement;
    }


    // 가게 폐업 신청
    @Transactional
    public StoreManagement close(String storeId) {
        StoreManagement storeManagement = storeManagementRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store ID not found: " + storeId));

        //TEMP_CLOSE이라면 CLOSURE_PENDING은 제가 멋대로 만든거니 수정하시면 됩니다!
        if (storeManagement.getStoreStatus() == StoreStatus.TEMP_CLOSE) {
            storeManagement.setStoreStatus(StoreStatus.CLOSURE_PENDING);

            storeApplicationRepository.save(
                    StoreApplication.builder()
                            .storeManagement(storeManagement)
                            .storeApplicationType(StoreApplicationType.CLOSURE)
                            .storeIsApproved(StoreIsApproved.WAIT)
                            .build()
            );

            return storeManagementRepository.save(storeManagement);
        }

        throw new IllegalStateException("Cannot apply for closure: Store is not in TEMP_CLOSE status.");
    }




}
