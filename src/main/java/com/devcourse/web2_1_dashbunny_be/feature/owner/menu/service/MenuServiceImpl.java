package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * .
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

  private final Validator validator;
  private final StoreManagementRepository storeManagementRepository;
  private final MenuRepository menuRepository;
  private final MenuGroupRepository menuGroupRepository;

  // 그룹 메뉴 조회
  @Override
  public List<MenuManagement> findGroupMenu(String groupId) {
  // 그룹 ID에 해당하는 메뉴 리스트 조회
    return null;
  /*menuRepository.findByGroupId(groupId);*/
  }

  //전체 메뉴 조회(메뉴 관리 1페이지 목록)
  @Override
  public List<MenuManagement> findStoreAllMenu(String storeId) {
    StoreManagement store = validator.validateStoreId(storeId);
    return menuRepository.findAllByStoreId(storeId);
  }

  // 메뉴명 기준으로 메뉴 검색
  @Override
  public List<MenuManagement> findSearchMenuName(String menuName) {
    return null;
    /*menuRepository.findByMenuNameContaining(menuName);*/
  }

  /**
  * 새로운 메뉴 등록을 위한 api service.
  */
  @Override
  public void create(
          String storeId, MenuManagement menu, CreateMenuRequestDto createMenuRequestDto) {
    StoreManagement store = validator.validateStoreId(storeId);

    if (createMenuRequestDto.getMenuGroupId() != null) {
      MenuGroup group = validator.validateGroupId(createMenuRequestDto.getMenuGroupId());
      menu.setMenuGroup(group);
    }

    menu.setStoreId(storeId);
    menuRepository.save(menu);
  }

  @Override
  public void updateAll(Long menuId, UpdateMenuRequestDto updateMenuRequestDto) {
    MenuManagement menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
    if (updateMenuRequestDto.getMenuName() != null) {
      menu.setMenuName(updateMenuRequestDto.getMenuName());
    }
    if (updateMenuRequestDto.getMenuGroupId() != null) {
      MenuGroup menuGroup = menuGroupRepository.findById(updateMenuRequestDto.getMenuGroupId())
                    .orElseThrow(() -> new RuntimeException("메뉴 그룹을 찾을 수 없습니다."));
      menu.setMenuGroup(menuGroup);
    }
    if (updateMenuRequestDto.getPrice() != null) {
      menu.setPrice(updateMenuRequestDto.getPrice());
    }
    if (updateMenuRequestDto.getMenuContent() != null) {
      menu.setMenuContent(updateMenuRequestDto.getMenuContent());
    }
    if (updateMenuRequestDto.getStockAvailable() != null) {
      menu.setStockAvailable(updateMenuRequestDto.getStockAvailable());
    }
    if (updateMenuRequestDto.getMenuStock() != null) {
      menu.setMenuStock(Math.toIntExact(updateMenuRequestDto.getMenuStock()));
    }
    if (updateMenuRequestDto.getIsSoldOut() != null) {
      menu.setIsSoldOut(updateMenuRequestDto.getIsSoldOut());
    }
    menuRepository.save(menu);
  }

  /**
  * 다중 메뉴 품절 처리.
  */
  @Override
  public void updateActionIsSoldOut(UpdateActionRequestDto actionRequestDto) {
    for (Long menuId : actionRequestDto.getMenuIds()) {
      MenuManagement menu = validator.validateMenuId(menuId);
      menu.setIsSoldOut(true);
      menuRepository.save(menu);
    }
  }

  @Override
  public void updateImage(Long menuId, UpdateMenuImageRequestDto imageUrlDto) {
    MenuManagement menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다."));
    menu.setMenuImage(imageUrlDto.getImageUrl()); // 이미지 URL 업데이트
    menuRepository.save(menu); // 변경 내용 저장
  }


  @Override
  public void updateIsSoldOut(Long menuId, UpdateSoldOutRequestDto updateSoldOutRequestDto) {
    MenuManagement menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다."));
    menu.setIsSoldOut(updateSoldOutRequestDto.getIsSoldOut()); // 품절 상태 업데이트
    menuRepository.save(menu); // 변경 내용 저장
  }

  /**
   * 다중 메뉴 삭제를 위한 api service.
   */
  @Override
  public void delete(UpdateActionRequestDto actionRequestDto) {
    for (Long menuId : actionRequestDto.getMenuIds()) {
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

  /**
  * 메뉴 단 건 삭제.
  */
  @Override
  public void deleteMenu(Long menuId) {
    menuRepository.deleteById(menuId);
  }
}
