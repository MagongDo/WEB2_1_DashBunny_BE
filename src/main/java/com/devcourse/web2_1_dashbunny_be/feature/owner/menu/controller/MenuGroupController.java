package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.controller;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.CreateMenuGroupRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuGroupListResponseDTO;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuGroupRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//메뉴 그룹 CRUD controller
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class MenuGroupController {

    private final GroupService groupService;

    //특정 가게 id의 그룹 목록을 반환
    @GetMapping("/read/group/{storeId}")
    public ResponseEntity<List<MenuGroupListResponseDTO>> readMenuGroup(@PathVariable String storeId) {
        List<MenuGroup> menuGroups =  groupService.read(storeId);
        List<MenuGroupListResponseDTO> responseDTOS = menuGroups.stream()
                .map(MenuGroupListResponseDTO :: fromEntity)
                .toList();
        return ResponseEntity.ok(responseDTOS);
    }

    //새로운 메뉴 그롭 생성을 위한 요청
    @PostMapping("/creat/group")
    public ResponseEntity<String> createGroup(@RequestBody CreateMenuGroupRequestDTO createMenuGroupRequestDTO){
        MenuGroup menuGroup = createMenuGroupRequestDTO.toEntity();
        groupService.save(menuGroup);
        return ResponseEntity.ok("그룹 성공적으로 등록되었습니다.");
    }

    //기존 그룹 정보 수정을 위한 요청
    @PatchMapping("/update/group/{groupId}")
    public ResponseEntity<String> updateGroup(@PathVariable String groupId,
                                              @RequestBody UpdateMenuGroupRequestDTO updateMenuGroupRequestDTO){
        groupService.update(groupId,updateMenuGroupRequestDTO);
        return ResponseEntity.ok("그룹 이름이 성공적으로 수정되었습니다.");
    }

    //그룹 정보 삭제를 위한 요청
    @DeleteMapping("/delete/group/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable String groupId){
        groupService.delete(groupId);
        return ResponseEntity.ok("그룹이 성공적으로 삭제되었습니다.");
    }

}
