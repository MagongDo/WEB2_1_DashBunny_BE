package com.devcourse.web2_1_dashbunny_be.feature.admin.store.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreIsApproved;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreClosureRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.StoreApplicationRepository;

import java.util.List;

import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 가게 등록 service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StoreApplicationService {
  private final StoreApplicationRepository storeApplicationRepository;
  private final StoreManagementRepository storeManagementRepository;


  /**
   * 가게 등록 승인 메서드.
   */
  @Transactional
  public void approve(String storeId) {
    StoreManagement storeManagement = storeManagementRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store not found for ID: " + storeId));
    StoreApplication storeApplication = storeApplicationRepository.findByStoreIdAndCreateAndWait(storeId);

    if (storeApplication == null) {
      throw new IllegalArgumentException("Application not found for store ID: " + storeId);
    }

    log.info("Approving store registration for store ID: {}", storeId);

    storeManagement.setStoreStatus(StoreStatus.REGISTERED);
    storeApplication.approve(); //승인
    storeManagement.setApprovedDate(storeApplication.getApprovedDate()); // 승인 날짜 갱신
    storeManagementRepository.save(storeManagement);

    storeApplication.setStoreIsApproved(StoreIsApproved.APPROVE); //승인 상태
    storeApplicationRepository.save(storeApplication);

    log.info("Store registration approved successfully for store ID: {}", storeId);
  }

  /**
   * 가게 등록 거절 메서드.
   */
  @Transactional
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


  /**
   * 가게 폐업 승인 메서드.
   */
  public void close(String storeId ) {
    StoreManagement storeManagement = storeManagementRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("Store not found for ID: " + storeId));
    StoreApplication storeApplication = storeApplicationRepository.findByStoreIdAndClosure(storeId);

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


  /**
   * 가게 번호로 단일 정보 조회 메서드.
   */
  public AdminStoreResponseDto getStore(String storeId) {
    StoreManagement store = storeManagementRepository.findById(storeId)
            .orElseThrow(() -> new IllegalArgumentException("not found storeId: " + storeId));
    return new AdminStoreResponseDto(store);
  }


//  /**
//   * 가게 목록 조회 메서드.
//   */
//  public List<AdminStoreListResponseDto> getStores() {
//    List<StoreManagement> stores = storeManagementRepository.findAll();
//    return stores.stream()
//            .map(AdminStoreListResponseDto::new)
//            .toList();
//  }


  /**
   * 가게 상태에 따른 가게 목록 조회 메서드.
   * @param status 가게 상태
   * @param page 페이지 번호
   * @param size 한 페이지당 데이터 수
   * @return 페이징된 가게 목록
   */
  public Page<AdminStoreListResponseDto> getStores(String status, int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size, Sort.by("approvedDate").descending()); //승인 날짜기준으로 내림차순 정렬
    Page<StoreManagement> stores;

    // 상태별 필터링
    if ("ENTIRE".equalsIgnoreCase(status)) { //가게 등록 신청
      stores = storeManagementRepository.findAll(pageable);
    }else if ("PENDING".equalsIgnoreCase(status)) { //가게 등록 신청
      stores = storeManagementRepository.findByStoreStatus(StoreStatus.PENDING, pageable);
    } else if ("CLOSURE_PENDING".equalsIgnoreCase(status)) { //가게 폐업 신청
      stores = storeManagementRepository.findByStoreStatus(StoreStatus.CLOSURE_PENDING, pageable);
    } else if ("OPEN".equalsIgnoreCase(status)) { //영업중
      stores = storeManagementRepository.findByStoreStatus(StoreStatus.OPEN, pageable);
    } else if ("CLOSE".equalsIgnoreCase(status)) { //영업 종료 (가게 상태 in 영업준비중 , 휴업중 )
      stores = storeManagementRepository.findByStoreStatusIn(
              List.of(StoreStatus.PENDING_OPEN, StoreStatus.TEMP_CLOSE), pageable
      );
    } else {
      // 기본 동작: 전체 목록 조회
      stores = storeManagementRepository.findAll(pageable);
    }

    return stores.map(AdminStoreListResponseDto::new); // Entity -> DTO 변환
  }

}
