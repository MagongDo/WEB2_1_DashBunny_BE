package com.devcourse.web2_1_dashbunny_be.feature.owner.shorts.service;


import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts.ShortsCreateRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreOperationInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ShortsService {
    private final StoreManagementRepository storeManagementRepository;
    private final StoreOperationInfoRepository storeOperationInfoRepository;
    private final MenuRepository menuRepository;

    /** 쇼츠 url 저장
     * @param requestDto 쇼츠 URL, storeID, menuId가 포함된 Dto
     * @return 저장결과
     */
    @Transactional
    public StoreOperationInfo createShorts(ShortsCreateRequestDto requestDto) {
        // StoreManagement 및 MenuManagement 조회
        StoreManagement storeId = storeManagementRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 storeId: " + requestDto.getStoreId()));

        MenuManagement menuId = menuRepository.findById(requestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 menuId: " + requestDto.getMenuId()));

        // Shorts 엔티티 생성
        StoreOperationInfo shorts = StoreOperationInfo.builder()
                .shortsUrl(requestDto.getUrl())
                .store(storeId)
                .menuId(menuId)
                .build();

        // 저장
        return storeOperationInfoRepository.save(shorts);
    }



}
