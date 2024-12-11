package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import kotlin.jvm.internal.SerializedIr;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * 메뉴 생성을 위한 DTO 클래스.
 */
@Getter
@Builder
public class CreateMenuRequestDto implements Serializable {
  private String menuName;
  private Long menuGroupId;
  private Long price;
  private String menuContent;
  private boolean stockAvailable;
  private int menuStock;
  private boolean isSoldOut;

  /**
   * 메뉴 엔티티 변환.
   */
  public MenuManagement toEntity() {
    MenuManagement menu = new MenuManagement();
    menu.setMenuName(menuName);
    menu.setPrice(price);
    menu.setMenuContent(menuContent);
    menu.setStockAvailable(stockAvailable);
    menu.setMenuStock(menuStock);
    menu.setIsSoldOut(isSoldOut);
    return menu;
    }
}
