package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import lombok.Builder;

/**
 * 운영정보 추가/저장을 위한 디티오 클래스.
 */
@Builder
public class CreateOperationInfoResponseDto {
  private String openingHours;
  private String breakTime;
  private String holidayDays;
  private String holidayNotice;

  /**
   *디티오 엔티티 변환 메서드.
  */
  public StoreOperationInfo toEntity() {
    StoreOperationInfo operationInfo = new StoreOperationInfo();
    operationInfo.setOpeningHours(openingHours);
    operationInfo.setBreakTime(breakTime);
    operationInfo.setHolidayDays(holidayDays);
    operationInfo.setHolidayNotice(holidayNotice);
    return operationInfo;
  }
}
