package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final Validator validator;
    private final StoreManagementRepository storeManagementRepository;
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;

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
        // 메뉴명 기준으로 메뉴 검색
        return menuRepository.findByMenuNameContaining(menuName);
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
        MenuManagement menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        if (updateMenuRequestDTO.getMenuName() != null) {
            menu.setMenuName(updateMenuRequestDTO.getMenuName());
        }
        if (updateMenuRequestDTO.getMenuGroupId() != null) {
            MenuGroup menuGroup = menuGroupRepository.findById(updateMenuRequestDTO.getMenuGroupId())
                    .orElseThrow(() -> new RuntimeException("메뉴 그룹을 찾을 수 없습니다."));
            menu.setMenuGroup(menuGroup);
        }
        if (updateMenuRequestDTO.getPrice() != null) {
            menu.setPrice(updateMenuRequestDTO.getPrice());
        }
        if (updateMenuRequestDTO.getMenuContent() != null) {
            menu.setMenuContent(updateMenuRequestDTO.getMenuContent());
        }
        if (updateMenuRequestDTO.getStockAvailable() != null) {
            menu.setStockAvailable(updateMenuRequestDTO.getStockAvailable());
        }
        if (updateMenuRequestDTO.getMenuStock() != null) {
            menu.setMenuStock(Math.toIntExact(updateMenuRequestDTO.getMenuStock()));
        }
        if (updateMenuRequestDTO.getIsSoldOut() != null) {
            menu.setIsSoldOut(updateMenuRequestDTO.getIsSoldOut());
        }
        menuRepository.save(menu);
    }

  /**
  * 다중 메뉴 품절 처리.
  */
  @Override
  public void updateActionIsSoldOut(UpdateActionRequestDto actionRequestDTO) {
      for(Long menuId : actionRequestDTO.getMenuIds()){
          MenuManagement menu = validator.validateMenuId(menuId);
          menu.setIsSoldOut(true);
          menuRepository.save(menu);
      }
  }

    @Override
    public void updateImage(Long menuId, UpdateMenuImageRequestDto imageUrlDTO) {

    }

    @Override
    public void updateIsSoldOut(Long menuId, UpdateSoldOutRequestDto updateSoldOutRequestDTO) {

    }

  /**
   * 다중 메뉴 삭제를 위한 api service.
   */
  @Override
  public void delete(UpdateActionRequestDto actionRequestDTO) {
     for(Long menuId : actionRequestDTO.getMenuIds()) {
        menuRepository.deleteById(menuId);
     }
    }

    @Override
    public MenuWithMenuGroupResponseDto MenuWithGroups(Long menuId) {
        MenuManagement menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        List<MenuGroup> menuGroups = menuGroupRepository.findAll();

        return MenuWithMenuGroupResponseDto.builder()
                .menu(MenuResponseDto.fromEntity(menu))
                .menuGroups(menuGroups.stream()
                        .map(group -> new MenuGroupResponseDto(group.getGroupId(), group.getGroupName()))
                        .collect(Collectors.toList()))
                .build();
    }
}
