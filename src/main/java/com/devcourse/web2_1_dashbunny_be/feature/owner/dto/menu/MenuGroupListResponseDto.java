package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 메뉴 그룹 종류 응답을 위한 DTO 클래스.
 */
@Setter
@Getter
@Builder
public class MenuGroupListResponseDto {
  private Long groupId;
  private String groupName;
  private Boolean isMainGroup;

  /**
   * 디티오 변환.
   */
  public static MenuGroupListResponseDto fromEntity(MenuGroup menuGroup) {
    return new MenuGroupListResponseDto(
         menuGroup.getGroupId(),
         menuGroup.getGroupName(),
         menuGroup.getIsMainGroup()
       );
    }
}
