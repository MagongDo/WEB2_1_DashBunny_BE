package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.*;

import java.util.List;

public interface MenuService {
    List<MenuManagement> findStoreAllMenu(String storeId); //
    List<MenuManagement> findGroupMenu(String groupId); // 해당 그룹 메뉴 조회
    List<MenuManagement> findSearchMenuName(String menuName);
    void create(Long storeId, MenuManagement menu); //새로운 메뉴 등록
    void updateAll(Long menuId,UpdateMenuRequestDTO updateMenuRequestDTO); //단 건 메뉴 모든 정보 수정 (필드 별 수정 가능)
    void updateActionIsSoldOut(Long menuId,UpdateActionRequestDTO actionRequestDTO); // 1페이지 품절 단 건 수정
    void updateImage(Long menuId, UpdateMenuImageRequestDTO imageUrlDTO); //1페이지 이미지 단 건 수정
    void updateIsSoldOut(Long menuId,UpdateSoldOutRequestDTO updateSoldOutRequestDTO);
    void delete(UpdateActionRequestDTO actionRequestDTO);
    MenuWithMenuGroupResponseDTO MenuWithGroups(Long meneId); //메뉴 수정 페이지 데이터 단 건 반환 및 그룹 반환
}
