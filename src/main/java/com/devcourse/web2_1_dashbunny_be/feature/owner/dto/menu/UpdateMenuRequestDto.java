package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 메뉴 정보 업데이트를 위한 DTO 클래스.
 */
@Getter
@Setter
public class UpdateMenuRequestDto implements Serializable {
  private String menuName;
  private Long menuGroupId;
  private Long price;
  private String menuContent;
  private Boolean stockAvailable; // 기본적으로 Boolean 사용 권장
  private Long menuStock;
  private Boolean isSoldOut;
}