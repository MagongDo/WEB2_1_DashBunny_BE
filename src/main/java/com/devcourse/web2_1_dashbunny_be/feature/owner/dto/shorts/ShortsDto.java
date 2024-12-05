package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShortsDto {
    private Long shortId;           // 쇼츠 ID
    private String url;             // 쇼츠 URL
    private String storeId;         // 가게 ID
    private String storeName;       // 가게 이름
    private Long menuId;            // 메뉴 ID
    private String menuName;        // 메뉴 이름
    private String menuContent;     // 메뉴 설명
    private String menuImage;       // 메뉴 이미지 (URL)
    private Long price;             // 메뉴 가격
}
