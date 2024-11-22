package com.devcourse.web2_1_dashbunny_be.feature.admin.store.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreApplicationType;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreIsApproved;
import com.devcourse.web2_1_dashbunny_be.domain.owner.Categorys;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreCreateRequestDTO;
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
    public StoreManagement create(StoreCreateRequestDTO storeCreateRequestDTO) {
        StoreManagement savedStoreManagement = storeManagementRepository.save(storeCreateRequestDTO.toEntity());
        storeApplicationRepository.save(
                StoreApplication.builder()
                        .storeManagement(savedStoreManagement)
                        .storeApplicationType(StoreApplicationType.CREATE)
                        .storeIsApproved(StoreIsApproved.WAIT)
                        .build()
        );
        return savedStoreManagement;
    }

    //가게 재등록 신청
    @Transactional
    public StoreManagement reCreate(String storeId, StoreCreateRequestDTO storeCreateRequestDTO) {
        StoreManagement storeManagement = storeManagementRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store ID not found: " + storeId));

        // StoreManagement 정보 업데이트
        storeManagement.setStoreName(storeCreateRequestDTO.getStoreName());
        storeManagement.setContactNumber(storeCreateRequestDTO.getContactNumber());
        storeManagement.setDescription(storeCreateRequestDTO.getDescription());
        storeManagement.setAddress(storeCreateRequestDTO.getAddress());
        storeManagement.setStoreRegistrationDocs(storeCreateRequestDTO.getStoreRegistrationDocs());
        storeManagement.setStoreBannerImage(storeCreateRequestDTO.getStoreBannerImage());
        storeManagement.setStoreStatus(StoreStatus.PENDING); // 상태를 재등록 신청 중으로 변경
        if (storeCreateRequestDTO.getCategories() != null) {
            List<Categorys> updatedCategories = storeCreateRequestDTO.getCategories().stream()
                    .map(type -> {
                        Categorys category = new Categorys();
                        category.setCategoryType(type);
                        category.setStoreManagement(storeManagement); // StoreManagement와 연결
                        return category;
                    })
                    .toList();

            storeManagement.setCategory(updatedCategories);
        }

        // 업데이트된 StoreManagement 저장
        StoreManagement savedStoreManagement = storeManagementRepository.save(storeManagement);

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
