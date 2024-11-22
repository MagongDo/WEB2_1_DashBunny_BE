package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import lombok.Getter;

/**
 * 메뉴 다중 삭제 / 품절처리를 위한 DTO 클래스.
 */
@Getter
public class UpdateActionRequestDto {
  private Long[] menuIds;
  private String action;
}
