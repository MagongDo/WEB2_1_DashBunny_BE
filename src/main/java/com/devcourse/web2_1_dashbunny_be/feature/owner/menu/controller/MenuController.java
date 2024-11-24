package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.controller;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateActionRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuImageRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateSoldOutRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 메뉴 리스트 페이지 컨트롤러.
 */
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;

  /**
   *전체 메뉴 조회.
   */
  @GetMapping("/menu/{storeId}")
  public ResponseEntity<List<MenuListResponseDto>> readMenu(@PathVariable String storeId) {
    List<MenuManagement> menus = menuService.findStoreAllMenu(storeId);
    List<MenuListResponseDto> result = menus.stream().map(MenuListResponseDto::fromEntity).toList();
    return ResponseEntity.ok(result);
  }

  /**
   * 그룹별 메뉴 조회.
   */
  @GetMapping("/group-menu/{groupId}")
  public ResponseEntity<List<MenuListResponseDto>> readGroupMenu(@PathVariable String groupId) {
    List<MenuManagement> groupMenus = menuService.findGroupMenu(groupId);
    List<MenuListResponseDto> responseDto = groupMenus.stream()
            .map(MenuListResponseDto::fromEntity).toList();
    return ResponseEntity.ok(responseDto);
  }

  /**
   *메뉴명 검색.
   */
  @GetMapping("/menu/search")
  public ResponseEntity<List<MenuListResponseDto>> searchMenu(
          @RequestParam("menuName") String menuName) {
    List<MenuManagement> groupMenus = menuService.findSearchMenuName(menuName);
    List<MenuListResponseDto> responseDto = groupMenus.stream()
            .map(MenuListResponseDto::fromEntity).toList();
    return ResponseEntity.ok(responseDto);
  }

  /**
   * 메뉴 이미지 등록 및 수정.
   */
  @PatchMapping("/menu/image/{menuId}")
  public ResponseEntity<String> updateMenuImage(
          @RequestParam("menuId") Long menuId,
          @RequestBody UpdateMenuImageRequestDto imageUrlDto) {
    menuService.updateImage(menuId, imageUrlDto);
    return ResponseEntity.ok( "메뉴 이미지가 성공적으로 업데이트되었습니다.");
  }

  /**
   * 다중 삭제 및 품절 처리.
   */
  @PatchMapping("/menu/action")
  public ResponseEntity<String> updateAction(
          @RequestBody UpdateActionRequestDto actionRequestDto) {
    if (actionRequestDto.getAction().equals("delete")) {
      menuService.delete(actionRequestDto);
    } else if (actionRequestDto.getAction().equals("SoldOut")) {
      menuService.updateActionIsSoldOut(actionRequestDto);
    }
    return ResponseEntity.ok("성공적으로 업데이트되었습니다.");
  }

  /**
   *단 건 품절 처리.
   */
  @PatchMapping("/menu-sold-out/{menuId}")
  public ResponseEntity<String> updateSoldOut(
          @PathVariable("menuId") Long menuId,
          @RequestBody UpdateSoldOutRequestDto updateSoldOutRequestDto) {
    menuService.updateIsSoldOut(menuId, updateSoldOutRequestDto);
    return ResponseEntity.ok("품절 처리 완료되었습니다.");
  }

}
