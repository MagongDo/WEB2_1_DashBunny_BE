package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 메뉴 그룹 정보를 나타내는 DTO 클래스.
 */
@Getter
@AllArgsConstructor
public class MenuGroupResponseDto {
    private Long groupId;
    private String groupName;
}