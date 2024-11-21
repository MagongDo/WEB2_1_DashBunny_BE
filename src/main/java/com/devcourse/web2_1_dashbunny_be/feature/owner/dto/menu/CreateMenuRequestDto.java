package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import lombok.Builder;

/**
 * 메뉴 생성을 위한 DTO 클래스.
 */
@Builder
public class CreateMenuRequestDto {
  private String menuName;
  private Long menuGroupId;
  private Long price;
  private boolean stockAvailable;
  private int menuStock;
  private boolean isSoldOut;

  /**
   * 메뉴 엔티티 변환.
   */
  public MenuManagement toEntity() {
    MenuManagement menu = new MenuManagement();
    menu.setMenuName(menuName);
    menu.getMenuGroup().setGroupId(menuGroupId);
    menu.setPrice(price);
    menu.setStockAvailable(stockAvailable);
    menu.setMenuStock(menuStock);
    menu.setIsSoldOut(isSoldOut);
    return menu;
    }
}
