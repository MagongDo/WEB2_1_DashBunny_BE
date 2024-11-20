package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;

//그룹 정보 수정을 위한 DTO 클래스
public class UpdateMenuGroupRequestDTO {
    private String groupName;

    public MenuGroup toEntity(){
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setGroupName(groupName);
        return menuGroup;
    }
}
