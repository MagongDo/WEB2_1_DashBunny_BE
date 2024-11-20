package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    public List<MenuManagement> findStore(String storeId) {
    return null;
    }

    public void updateImage(Long menuId, UpdateMenuImageRequestDTO imageUrlDTO) {

    }

    public void delete(UpdateActionRequestDTO actionRequestDTO) {
    }

    public void updateIsSoldOut(UpdateActionRequestDTO actionRequestDTO) {
    }

    public void update(UpdateSoldOutRequestDTO updateSoldOutRequestDTO) {
    //솔드아웃 상태 변화

    }

    // 해당 그룹 메뉴 조회
    public List<MenuManagement> findGroupMenu(String groupId) {
   return null;
    }

    public List<MenuManagement> findSearchMenuName(String menuName) {
        return null;
    }

    //그룹 이름 조회
    public List<MenuGroup> findGroupMenuList(String storeId) {
    return null;
    }

    //새로운 메뉴 등록
    public void create(MenuManagement menu) {
    }

    //메뉴 수정 페이지 데이터 단 건 반환 및 그룹 반환
    public MenuWithMenuGroupResponseDTO MenuWithGroups(Long meneId) {

    return null;}

    //단 건 메뉴 모든 정보 수정 (필드 별 수정 가능)
    public void updateAll(UpdateMenuRequestDTO updateMenuRequestDTO) {
    }
}
