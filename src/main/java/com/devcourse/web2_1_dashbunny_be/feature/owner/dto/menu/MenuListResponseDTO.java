package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import lombok.Builder;

@Builder
public class MenuListResponseDTO {
    private Long menuId;
    private String menuImage;
    private String menuName;
    private String menuGroupName;
    private Long price;
    private Boolean stockAvailable;
    private int menuStock;
    private Boolean isSoldOut;

    public static MenuListResponseDTO fromEntity(MenuManagement menu) {
        return new MenuListResponseDTO(
                menu.getMenuId(),
                menu.getMenuImage(),
                menu.getMenuName(),
                menu.getMenuGroup().getGroupName(),
                menu.getPrice(),
                menu.isStockAvailable(),
                menu.getMenuStock(),
                menu.getIsSoldOut()
        );

    }
}
