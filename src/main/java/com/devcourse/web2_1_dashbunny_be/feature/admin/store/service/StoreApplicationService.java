package com.devcourse.web2_1_dashbunny_be.feature.admin.store.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreIsApproved;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreListRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.StoreApplicationRepository;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.StoreManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class StoreApplicationService {
    private final StoreApplicationRepository storeApplicationRepository;
    private final StoreManagementRepository storeManagementRepository;


    // 가게 등록 승인
    public void approve(String storeId) {
        StoreManagement storeManagement = storeManagementRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found for ID: " + storeId));
        StoreApplication storeApplication = storeApplicationRepository.findByStoreIdAndCreateAndWait(storeId);

        if (storeApplication == null) {
            throw new IllegalArgumentException("Application not found for store ID: " + storeId);
        }

        log.info("Approving store registration for store ID: {}", storeId);

        storeManagement.setStoreStatus(StoreStatus.REGISTERED);
        storeApplication.approve();
        storeManagement.setApprovedDate(storeApplication.getApprovedDate()); // 승인 날짜 갱신
        storeManagementRepository.save(storeManagement);

        storeApplication.setStoreIsApproved(StoreIsApproved.APPROVE);
        storeApplicationRepository.save(storeApplication);

        log.info("Store registration approved successfully for store ID: {}", storeId);
    }

    // 가게 등록 거절
    public void reject(String storeId, String reason) {
        StoreManagement storeManagement = storeManagementRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found for ID: " + storeId));
        StoreApplication storeApplication = storeApplicationRepository.findByStoreIdAndCreateAndWait(storeId);

        if (storeApplication == null) {
            throw new IllegalArgumentException("Application not found for store ID: " + storeId);
        }

        log.info("Rejecting store registration for store ID: {}. Reason: {}", storeId, reason);

        storeManagement.setStoreStatus(StoreStatus.REGISTRATION); // 상태를 REJECTED로 설정
        storeManagementRepository.save(storeManagement);

        storeApplication.reject();
        storeApplication.setStoreIsApproved(StoreIsApproved.REJECT);
        storeApplication.setRejectionReason(reason); // 거절 사유 설정
        storeApplicationRepository.save(storeApplication);

        log.info("Store registration rejected successfully for store ID: {}", storeId);
    }


    // 가게 폐업 승인
    public void close(String storeId) {
        StoreManagement storeManagement = storeManagementRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found for ID: " + storeId));
        StoreApplication storeApplication = storeApplicationRepository.findByStoreIdAndClosure(storeId); //

        if (storeApplication == null) {
            throw new IllegalArgumentException("Application not found for store ID: " + storeId);
        }

        log.info("Approving store closure for store ID: {}", storeId);

        storeManagement.setStoreStatus(StoreStatus.CLOSED);
        storeApplication.approve();
        storeManagement.setApprovedDate(storeApplication.getApprovedDate()); // 승인 날짜 갱신
        storeManagementRepository.save(storeManagement);

        storeApplication.setStoreIsApproved(StoreIsApproved.APPROVE);
        storeApplicationRepository.save(storeApplication);

        log.info("Store closure approved successfully for store ID: {}", storeId);
    }


    //가게 번호로 단일 조회
    public AdminStoreRequestDTO getStore(String storeId) {
        StoreManagement store=storeManagementRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("not found storeId: " + storeId));
        return new AdminStoreRequestDTO(store);
    }

    //가게 목록 조회
    public List<AdminStoreListRequestDTO> getStores() {
        List<StoreManagement> stores=storeManagementRepository.findAll();
        return stores.stream()
                .map(AdminStoreListRequestDTO::new)
                .toList();
    }

}
