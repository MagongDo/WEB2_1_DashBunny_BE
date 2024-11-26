package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import lombok.Builder;
import lombok.Setter;

//메뉴 그룹 종류 응답을 위한 DTO 클래스
@Setter
@Builder
public class MenuGroupListResponseDTO {
    private Long groupId;
    private String groupName;

    public static MenuGroupListResponseDTO fromEntity(MenuGroup menuGroup) {
       return new MenuGroupListResponseDTO(
         menuGroup.getGroupId(),
         menuGroup.getGroupName()
       );
    }
}
