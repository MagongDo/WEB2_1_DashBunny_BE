package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import lombok.Builder;
import lombok.Getter;

/**
 * 운영정보 추가/저장을 위한 디티오 클래스.
 */
@Getter
@Builder
public class CreateOperationInfoResponseDto {
  private String openingHours;
  private String breakTime;
  private String holidayDays;
  private String holidayNotice;

}
