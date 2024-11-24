package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final Validator validator;
    private final StoreManagementRepository storeManagementRepository;
    private final MenuRepository menuRepository;

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

  /**
  * 새로운 메뉴 등록을 위한 api service.
  */
  @Override
  public void create(
          String storeId, MenuManagement menu, CreateMenuRequestDto createMenuRequestDto) {
    StoreManagement store = validator.validateStoreId(storeId);

    if(createMenuRequestDto.getMenuGroupId() !=null) {
        MenuGroup group = validator.validateGroupId(createMenuRequestDto.getMenuGroupId());
        menu.setMenuGroup(group);
    }

    menu.setStoreId(storeId);
    menuRepository.save(menu);
  }

    @Override
    public void updateAll(Long menuId, UpdateMenuRequestDto updateMenuRequestDTO) {

    }

    @Override
    public void updateActionIsSoldOut(Long menuId, UpdateActionRequestDto actionRequestDTO) {

    }

    @Override
    public void updateImage(Long menuId, UpdateMenuImageRequestDto imageUrlDTO) {

    }

    @Override
    public void updateIsSoldOut(Long menuId, UpdateSoldOutRequestDto updateSoldOutRequestDTO) {

    }

    @Override
    public void delete(UpdateActionRequestDto actionRequestDTO) {

    }

    @Override
    public MenuWithMenuGroupResponseDto MenuWithGroups(Long meneId) {
        return null;
    }
}
