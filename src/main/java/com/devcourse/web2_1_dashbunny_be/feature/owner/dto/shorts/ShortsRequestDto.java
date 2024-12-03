package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShortsRequestDto {

    private String userId;  // 사용자 ID
    private String address; // 사용자의 주소

}
