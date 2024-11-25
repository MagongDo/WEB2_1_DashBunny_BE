package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import lombok.Builder;

/**
 * 전체 메뉴 리스트 조회를 위한 DTO 클래스.
 */
@Builder
public class MenuListResponseDto {
  private Long menuId;
  private String menuImage;
  private String menuName;
  private String menuGroupName;
  private Long price;
  private Boolean stockAvailable;
  private int menuStock;
  private Boolean isSoldOut;

  /**
   * 디티오로 변환.
   */
  public static MenuListResponseDto fromEntity(MenuManagement menu) {
    return MenuListResponseDto.builder()
        .menuId(menu.getMenuId())
        .menuImage(menu.getMenuImage())
        .menuName(menu.getMenuName())
        .menuGroupName(menu.getMenuGroup().getGroupName())
        .price(menu.getPrice())
        .stockAvailable(menu.isStockAvailable())
        .menuStock(menu.getMenuStock())
        .isSoldOut(menu.getIsSoldOut())
        .build();
    }
}
