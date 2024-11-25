package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;

//새로운 메뉴 그룹 생성 정보를 받기 위한 DTO 클래스
public class CreateMenuGroupRequestDTO {
    private String groupName;

    public MenuGroup toEntity() {
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setGroupName(groupName);
        return menuGroup;
    }
}
