package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.controller;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.CreateMenuGroupRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuGroupListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuGroupRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.GroupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 메뉴 그룹 CRUD 컨트롤러.
 */
@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuGroupController {

  private final GroupService groupService;

  /**
  * 특정 가게 id의 그룹 목록을 반환.
  */
  @GetMapping("/group/{storeId}")
  public ResponseEntity<List<MenuGroupListResponseDto>> readMenuGroup(
          @PathVariable String storeId) {
    List<MenuGroup> menuGroups =  groupService.read(storeId);
    log.info("Menu groups read: " + menuGroups.size());
    List<MenuGroupListResponseDto> responseDto = menuGroups.stream()
              .map(MenuGroupListResponseDto::fromEntity)
              .toList();
    return ResponseEntity.ok(responseDto);
  }

  /**
   * 새로운 메뉴 그롭 생성을 위한 요청.
   */
  @PostMapping("/group/{storeId}")
  public ResponseEntity<String> createGroup(
          @RequestBody CreateMenuGroupRequestDto createMenuGroupRequestDto,
          @PathVariable("storeId") String storeId) {
    MenuGroup menuGroup = createMenuGroupRequestDto.toEntity();
    groupService.save(storeId, menuGroup);
    return ResponseEntity.ok("그룹 성공적으로 등록되었습니다.");
  }

  /**
   *기존 그룹 정보 수정을 위한 요청.
   */
  @PatchMapping("/group/{groupId}")
  public ResponseEntity<String> updateGroup(
          @PathVariable String groupId,
          @RequestBody UpdateMenuGroupRequestDto updateMenuGroupRequestDto) {
    groupService.update(groupId, updateMenuGroupRequestDto);
    return ResponseEntity.ok("그룹 이름이 성공적으로 수정되었습니다.");
  }

    /**
     * 그룹 정보 삭제를 위한 요청.
     */
  @DeleteMapping("/group/{groupId}")
  public ResponseEntity<String> deleteGroup(@PathVariable String groupId) {
    groupService.delete(groupId);
    return ResponseEntity.ok("그룹이 성공적으로 삭제되었습니다.");
  }
}
