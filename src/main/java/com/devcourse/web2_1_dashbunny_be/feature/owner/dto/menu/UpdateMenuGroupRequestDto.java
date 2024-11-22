package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import lombok.Builder;

/**
 * 그룹 정보 수정을 위한 DTO 클래스.
 */
@Builder
public class UpdateMenuGroupRequestDto {
  private String groupName;

  /**
   *엔티티 변환.
   */
  public MenuGroup toEntity() {
    MenuGroup menuGroup = new MenuGroup();
    menuGroup.setGroupName(groupName);
    return menuGroup;
  }
}
