package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersMenuDto {

    private Long menuId;            // 메뉴 ID
    private String menuName;        // 메뉴 이름
    private Long price;             // 메뉴 가격
    private String menuContent;     // 메뉴 설명
    private String menuImage;       // 메뉴 이미지 URL
    private Integer menuOption;     // 메뉴 옵션
    private Boolean isSoldOut;      // 재고 여부

    public static UsersMenuDto toMenuDTO(MenuManagement menu) {
        return UsersMenuDto.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .menuContent(menu.getMenuContent())
                .menuImage(menu.getMenuImage())
                .menuOption(menu.getMenuStock())
                .isSoldOut(menu.getIsSoldOut())
                .build();
    }
}