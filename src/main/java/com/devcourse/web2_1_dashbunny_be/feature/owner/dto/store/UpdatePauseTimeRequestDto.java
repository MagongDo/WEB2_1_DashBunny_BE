package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import lombok.Builder;
import lombok.Getter;

/**
 * 영업 일시 중지 상태 업데이트를 위한 DTO 클래스.
 */
@Getter
@Builder
public class UpdatePauseTimeRequestDto {
    private String pauseStartTime;
    private String pauseEndTime;
}
