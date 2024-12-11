package com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInfoResponseDto {
    private int userCount;
    private int ownerCount;

}
