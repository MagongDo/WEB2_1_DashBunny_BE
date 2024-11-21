package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private Validator validator;
    private StoreRepository storeRepository;
    private MenuRepository menuRepository;

    //전체 메뉴 조회(메뉴 관리 1페이지 목록)
    @Override
    public List<MenuManagement> findStoreAllMenu(String storeId) {
        StoreManagement store = validator.validateStoreId(storeId);
        return menuRepository.findAllByStoreId(storeId);
    }

    @Override
    public List<MenuManagement> findGroupMenu(String groupId) {
        return List.of();
    }

    @Override
    public List<MenuManagement> findSearchMenuName(String menuName) {
        return List.of();
    }

    @Override
    public void create(Long storeId, MenuManagement menu) {

    }

    @Override
    public void updateAll(Long menuId, UpdateMenuRequestDTO updateMenuRequestDTO) {

    }

    @Override
    public void updateActionIsSoldOut(Long menuId, UpdateActionRequestDTO actionRequestDTO) {

    }

    @Override
    public void updateImage(Long menuId, UpdateMenuImageRequestDTO imageUrlDTO) {

    }

    @Override
    public void updateIsSoldOut(Long menuId, UpdateSoldOutRequestDTO updateSoldOutRequestDTO) {

    }

    @Override
    public void delete(UpdateActionRequestDTO actionRequestDTO) {

    }

    @Override
    public MenuWithMenuGroupResponseDTO MenuWithGroups(Long meneId) {
        return null;
    }
}
