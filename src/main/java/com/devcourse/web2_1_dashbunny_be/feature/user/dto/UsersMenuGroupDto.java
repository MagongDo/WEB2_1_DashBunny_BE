package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersMenuGroupDto {

  private Long groupId;                // 메뉴 그룹 ID
  private String groupName;            // 그룹 이름
  private List<UsersMenuDto> menus;         // 메뉴 리스트

  public static UsersMenuGroupDto toMenuGroupDto(MenuGroup group) {
    return UsersMenuGroupDto.builder()
      .groupId(group.getGroupId())
      .groupName(group.getGroupName())
      .menus(group.getMenuList().stream()
      .map(UsersMenuDto::toMenuDto)
      .toList())
      .build();
  }
}