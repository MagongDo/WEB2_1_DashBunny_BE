package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.controller;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.CreateMenuRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuGroupListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.GroupService;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 메뉴 등록 페이지 컨트롤러.
 */
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuCreateController {

  private final MenuService menuService;
  private final GroupService groupService;

  /**
  * 메뉴 그룹 조회.
  */
  @GetMapping("/create-menu/{storeId}")
  public ResponseEntity<List<MenuGroupListResponseDto>> readMenuGroup(
          @PathVariable String storeId) {
    List<MenuGroup> menuGroups = groupService.read(storeId);
    List<MenuGroupListResponseDto> responseDto = menuGroups.stream()
            .map(MenuGroupListResponseDto::fromEntity).toList();
    return ResponseEntity.ok(responseDto);
  }

  /**
   *새로운 메뉴 등록(이미지 X).
   */
  @PostMapping("/create-menu/{storeId}")
  public ResponseEntity<String> creatMenu(
          @PathVariable("storeId") Long storeId,
          @RequestBody CreateMenuRequestDto createMenuRequestDto) {
    MenuManagement menu = createMenuRequestDto.toEntity();
    menuService.create(storeId, menu);
    return ResponseEntity.ok("메뉴가 성공적으로 등록되었습니다.");
  }

}
