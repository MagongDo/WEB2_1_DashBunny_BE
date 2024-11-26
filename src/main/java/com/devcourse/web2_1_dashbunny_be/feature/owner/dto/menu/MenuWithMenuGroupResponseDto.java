package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

/**
 * 메뉴 수정 시 메뉴 정보와 그룹의 정보를 한 번에 조회하기 위한 DTO 클래스.
 */
@Getter
@Builder
public class MenuWithMenuGroupResponseDto {
  private MenuResponseDto menu;             // 단건 메뉴 정보
  private List<MenuGroupResponseDto> menuGroups; // 메뉴 그룹 정보 리스트
}