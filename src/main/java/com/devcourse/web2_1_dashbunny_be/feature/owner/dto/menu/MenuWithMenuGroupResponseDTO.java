package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import lombok.Builder;


//메뉴 수정 시 메뉴 정보와 그룹의 정보를 한번에 조회하기 위한 DTO 클래스
@Builder
public class MenuWithMenuGroupResponseDTO {
    private MenuResponseDTO menu;
    private MenuListResponseDTO menuList;
}

