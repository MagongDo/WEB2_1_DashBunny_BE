package com.devcourse.web2_1_dashbunny_be.feature.owner.common;

import com.devcourse.web2_1_dashbunny_be.domain.owner.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.BasicInfoProjection;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository.OwnerCouponRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreOperationInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 검증 클래스.
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class Validator {

    private final MenuGroupRepository menuGroupRepository;
    private final MenuRepository menuRepository;
    private final StoreManagementRepository storeManagementRepository;
    private final StoreOperationInfoRepository storeOperationInfoRepository;
    private final OwnerCouponRepository ownerCouponRepository;

    //메뉴 그룹 아이디 검증 메서드
    public MenuGroup validateGroupId(Long groupId) {
        return menuGroupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티를 찾을 수 없습니다."));
    }

    //메뉴 아이디 검증 메서드
    public MenuManagement validateMenuId(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException("해당 메뉴 엔티티를 찾을 수 없습니다."));
    }

    //가게 아이디 검증 메서드
    public StoreManagement validateStoreId(String storeId) {
        log.info("Validating storeId: {}", storeId); // 로그 추가
        return storeManagementRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found for ID: " + storeId));
    }

    //가게 아이디 검증 메서드
    public BasicInfoProjection validateBasicStoreId(String storeId) {
        BasicInfoProjection basicInfo = storeManagementRepository.findBasicInfoByStoreId(storeId);
        if(basicInfo == null) {
            new EntityNotFoundException("가게의 기본 정보를 불러올 수 없습니다.");
        }
        return basicInfo;
    }

    //가게 운영정보 검증 메서드
    public StoreOperationInfo validateOperationStoreId(Long operationId) {
        log.info("검증 시작");
        StoreOperationInfo operationInfo = storeOperationInfoRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("가게의 운영 정보를 불러올 수 잆습니다."));
        return operationInfo;
    }

    /**
     * 쿠폰 검증 메서드.
     */
    public OwnerCoupon validateCouponId(Long couponId) {
      return ownerCouponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("쿠폰 정보를 찾을 수 없습니다."));
    }
}
