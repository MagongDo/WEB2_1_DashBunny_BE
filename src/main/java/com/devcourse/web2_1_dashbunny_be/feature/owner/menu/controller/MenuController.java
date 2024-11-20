package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.controller;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.GroupService;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final GroupService groupService;
    //전체 메뉴 조회(메뉴 관리 1페이지 목록)
    @GetMapping("/read/menu/{storeId}")
    public ResponseEntity<List<MenuListResponseDTO>> readMenu(@PathVariable String storeId) {
        List<MenuManagement> menus = menuService.findStore(storeId);
        List<MenuListResponseDTO> result = menus.stream().map(MenuListResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(result);
    }

    //메뉴 그룹 조회(메뉴 관리 2페이지 그룹 드롭다운 용 목록)
    @GetMapping("/create/menu/{storeId}")
    public ResponseEntity<List<MenuGroupListResponseDTO>> readMenuGroup(@PathVariable String storeId) {
        List<MenuGroup> menuGroups = groupService.read(storeId);
        List<MenuGroupListResponseDTO> responseDTOS = menuGroups.stream().map(MenuGroupListResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(responseDTOS);
    }

    //메뉴 그룹 및 메뉴 정보 조회(메뉴 수정 페이지 용 목록)
    @GetMapping("/update/menu/{meneId}")
    public ResponseEntity<MenuWithMenuGroupResponseDTO> updateMenuReadList(@PathVariable Long menuId) {
        MenuWithMenuGroupResponseDTO responseDTOS = menuService.MenuWithGroups(menuId);
        return ResponseEntity.ok(responseDTOS);
    }

    //그룹별 메뉴 조회
    @GetMapping("/read/group-menu/{groupId}")
    public ResponseEntity<List<MenuListResponseDTO>> readGroupMenu(@PathVariable String groupId) {
        List<MenuManagement> groupMenus = menuService.findGroupMenu(groupId);
        List<MenuListResponseDTO> responseDTOS = groupMenus.stream().map(MenuListResponseDTO :: fromEntity).toList();
        return ResponseEntity.ok(responseDTOS);
    }

    //메뉴명 검색
    @GetMapping("/menu/search")
    public ResponseEntity<List<MenuListResponseDTO>> searchMenu(@RequestParam("menuName") String menuName) {
        List<MenuManagement> groupMenus = menuService.findSearchMenuName(menuName);
        List<MenuListResponseDTO> responseDTOS = groupMenus.stream().map(MenuListResponseDTO::fromEntity).toList();
        return ResponseEntity.ok(responseDTOS);
    }

    //새로운 메뉴 등록(이미지 X)
    @PostMapping("/store/create/menu")
    public ResponseEntity<String> creatMenu(@RequestBody CreateMenuRequestDTO createMenuRequestDTO) {
        MenuManagement menu = createMenuRequestDTO.toEntity();
        menuService.create(menu);
        return ResponseEntity.ok("메뉴가 성공적으로 등록되었습니다.");
    }

    //메뉴 이미지 등록 및 수정
    @PatchMapping("/update/menu/image/{menuId}")
    public ResponseEntity<String> updateMenuImage(@RequestParam Long menuId,
                                                  @RequestBody UpdateMenuImageRequestDTO imageUrlDTO) {
        menuService.updateImage(menuId,imageUrlDTO);
        return ResponseEntity.ok( "메뉴 이미지가 성공적으로 업데이트되었습니다.");
    }

    //다중 삭제 및 품절 처리
    @PatchMapping("/read/menu/action")
    public ResponseEntity<String> updateAction(@RequestBody UpdateActionRequestDTO actionRequestDTO) {
        if(actionRequestDTO.getAction().equals("delete")){
            menuService.delete(actionRequestDTO);
        } else if (actionRequestDTO.getAction().equals("SoldOut")) {
            menuService.updateIsSoldOut(actionRequestDTO);
        }
        return ResponseEntity.ok("성공적으로 업데이트되었습니다.");
    }

    //단 건 품절 처리
    @PatchMapping("/update/menu-sold-out/{menuId}")
    public ResponseEntity<String> updateSoldOut(@PathVariable Long menuId,
                                                @RequestBody UpdateSoldOutRequestDTO updateSoldOutRequestDTO) {
        menuService.update(updateSoldOutRequestDTO);
        return ResponseEntity.ok("품절 처리 완료되었습니다.");
    }

    //단 건 메뉴 수정 요청
    @PatchMapping("/update/menu/{meneId}")
    public ResponseEntity<String> updateMenu(@PathVariable Long meneId,
                                             @RequestBody UpdateMenuRequestDTO updateMenuRequestDTO) {
        menuService.updateAll(updateMenuRequestDTO);
        return ResponseEntity.ok("메뉴 정보가 성공적으로 업데이트되었습니다.");
    }



}
