package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.controller;

import com.devcourse.web2_1_dashbunny_be.config.s3.FileUploadService;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateActionRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuImageRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateSoldOutRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.MenuService;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;


/**
 * 메뉴 리스트 페이지 컨트롤러.
 */
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;
  private final FileUploadService fileUploadService;

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
   *전체 메뉴 조회.
   */
  @GetMapping("/menu/{storeId}")
  public ResponseEntity<List<MenuListResponseDto>> readMenu(@PathVariable String storeId) {
    List<MenuManagement> menus = menuService.findStoreAllMenu(storeId);
    List<MenuListResponseDto> result = menus.stream().map(MenuListResponseDto::fromEntity).toList();
    return ResponseEntity.ok(result);
  }

  /**
   * 메뉴 이미지 등록 및 수정.
   */
  @PatchMapping("/update/menu/image/{storeId}/{menuId}")
  public ResponseEntity<String> updateMenuImage(
          @PathVariable("menuId") Long menuId,
          @PathVariable("storeId") String storeId,
          @RequestParam("menuImageFile") MultipartFile menuImageFile) {
    try {
      String fileUrl = fileUploadService.uploadFile(menuImageFile, "menuImage");
      menuService.updateImage(menuId, fileUrl, storeId);
      return ResponseEntity.ok("메뉴 이미지가 성공적으로 업데이트되었습니다.");
    } catch (MultipartException e) {
      return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * 다중 삭제 및 품절 처리.
   */
  @PatchMapping("/menu/action/{storeId}")
  public ResponseEntity<String> updateAction(
          @PathVariable("storeId") String storeId,
          @RequestBody UpdateActionRequestDto actionRequestDto) {
    if (actionRequestDto.getAction().equals("delete")) {
      menuService.delete(actionRequestDto, storeId);
    } else if (actionRequestDto.getAction().equals("SoldOut")) {
      menuService.updateActionIsSoldOut(actionRequestDto);
    }
    return ResponseEntity.ok("성공적으로 업데이트되었습니다.");
  }

  /**
  * 메뉴 단건 삭제.
  */
  @DeleteMapping("menu-delete/{storeId}/{menuId}")
  public ResponseEntity<String> deleteMenu(
          @PathVariable("storeId") String storeId,
          @PathVariable("menuId") Long menuId) {
    menuService.deleteMenu(menuId ,storeId);
    return ResponseEntity.ok("삭제가 완료되었습니다.");
  }


  /**
   *단 건 품절 처리.
   */
  @PatchMapping("/menu-sold-out/{storeId}/{menuId}")
  public ResponseEntity<String> updateSoldOut(
          @PathVariable("menuId") Long menuId,
          @PathVariable("storeId") String storeId,
          @RequestBody UpdateSoldOutRequestDto updateSoldOutRequestDto) {
    menuService.updateIsSoldOut(menuId, updateSoldOutRequestDto, storeId);
    return ResponseEntity.ok("품절 처리 완료되었습니다.");
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







}
