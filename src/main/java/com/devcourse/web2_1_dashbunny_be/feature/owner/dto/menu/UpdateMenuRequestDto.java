package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

/**
 * 메뉴 정보 업데이트를 위한 DTO 클래스.
 */
public class UpdateMenuRequestDto {
  private String menuName;
  private Long menuGroupId;
  private Long price;
  private String menuContent;
  private boolean stockAvailable;
  private Long menuStock;
  private boolean isSoldOut;
}

