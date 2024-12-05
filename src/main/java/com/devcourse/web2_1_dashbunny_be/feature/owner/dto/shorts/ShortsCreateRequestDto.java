package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShortsCreateRequestDto {

    private String url;          // URL
    private String storeId;        // 가게 ID
    private Long menuId;         // 메뉴 ID

}
