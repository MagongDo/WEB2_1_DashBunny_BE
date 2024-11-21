package com.devcourse.web2_1_dashbunny_be.feature.owner.common;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {

    private final MenuGroupRepository menuGroupRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

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
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("해당 가게 엔티티를 찾을 수 없습니다."));
    }

}
