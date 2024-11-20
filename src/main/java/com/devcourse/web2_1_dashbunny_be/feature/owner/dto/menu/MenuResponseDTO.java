package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import lombok.Builder;

@Builder
public class MenuResponseDTO {
    private Long menuId;
    private String menuName;
    private Long menuGroupId;
    private String menuGroupName;
    private Long price;
    private Boolean stockAvailable;
    private int menuStock;
    private Boolean isSoldOut;

    private MenuResponseDTO MenuDTO(MenuManagement menu) {
        return new MenuResponseDTO(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getMenuGroup().getGroupId(),
                menu.getMenuGroup().getGroupName(),
                menu.getPrice(),
                menu.isStockAvailable(),
                menu.getMenuStock(),
                menu.getIsSoldOut()
        );
    };
}

