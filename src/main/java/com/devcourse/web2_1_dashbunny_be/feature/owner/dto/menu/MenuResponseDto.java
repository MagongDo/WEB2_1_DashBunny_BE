package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import lombok.Builder;

/**
 * 메뉴 단 건 조회 DTO 클래스.
 */
@Builder
public class MenuResponseDto {
  private Long menuId;
  private String menuName;
  private Long menuGroupId;
  private String menuGroupName;
  private Long price;
  private Boolean stockAvailable;
  private int menuStock;
  private Boolean isSoldOut;

  /**
   * 디티오 변환.
   */
  public MenuResponseDto menuDto(MenuManagement menu) {
    return MenuResponseDto.builder()
               .menuId(menu.getMenuId())
               .menuName(menu.getMenuName())
               .menuGroupId(menu.getMenuGroup().getGroupId())
               .menuGroupName(menu.getMenuGroup().getGroupName())
               .price(menu.getPrice())
               .stockAvailable(menu.isStockAvailable())
               .menuStock(menu.getMenuStock())
               .isSoldOut(menu.getIsSoldOut())
               .build();
   }
}

