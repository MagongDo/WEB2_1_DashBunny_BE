package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import lombok.Builder;

/**
 * 새로운 메뉴 그룹 생성 정보를 받기 위한 DTO 클래스.
 */
@Builder
public class CreateMenuGroupRequestDto {
  private String groupName;

  /**
  * 메뉴 그룹 엔티티 변환.
  */
  public MenuGroup toEntity() {
    MenuGroup menuGroup = new MenuGroup();
    menuGroup.setGroupName(groupName);
    return menuGroup;
    }
}
