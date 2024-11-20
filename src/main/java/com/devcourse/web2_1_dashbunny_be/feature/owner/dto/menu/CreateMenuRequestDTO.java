package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import lombok.Builder;

@Builder
public class CreateMenuRequestDTO {
    private String menuName;
    private Long menuGroupId;
    private Long price;
    private boolean stockAvailable;
    private int menuStock;
    private boolean isSoldOut;

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
