package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 그룹 정보 수정을 위한 DTO 클래스.
 */
@Getter
@Setter
@Builder
public class UpdateMenuGroupRequestDto {
  private String groupName;
  private boolean isMainGroup;
}
