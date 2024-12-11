package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuWithMenuGroupResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.GroupService;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 메뉴 수정 페이지 컨트롤러.
 */
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuUpdateController {

  private final MenuService menuService;
  private final GroupService groupService;

  /**
   *메뉴 그룹 및 메뉴 정보 조회(메뉴 수정 페이지 용 목록) api.
   */
  @GetMapping("/update-menu/{menuId}")
  public ResponseEntity<MenuWithMenuGroupResponseDto> updateMenuData(@PathVariable Long menuId) {
    MenuWithMenuGroupResponseDto responseDtos = menuService.MenuWithGroups(menuId);
    return ResponseEntity.ok(responseDtos);
  }

  /**
   *메뉴 수정 요청 api.
   */
  @PatchMapping("/update-menu/{storeId}/{menuId}")
  public ResponseEntity<String> updateMenu(
          @PathVariable("menuId") Long menuId,
          @PathVariable("storeId") String storeId,
          @RequestBody UpdateMenuRequestDto updateMenuRequestDto) {
    menuService.updateAll(menuId, updateMenuRequestDto, storeId);
    return ResponseEntity.ok("메뉴 정보가 성공적으로 업데이트되었습니다.");
  }
}
