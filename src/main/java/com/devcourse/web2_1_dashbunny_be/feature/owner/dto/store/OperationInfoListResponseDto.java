package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import lombok.Builder;

/**
 * 가게 운영 정보 조회를 위한 DTO.
 */
@Builder
public class OperationInfoListResponseDto {
  private String openingHours;
  private String breakTime;
  private String holiday;
  private String holidayNotice;

  /**
   *엔티티를 디티오로 반환하는 메서드.
   */
  public OperationInfoListResponseDto fromEntity(StoreOperationInfo storeOperationInfo) {
    return OperationInfoListResponseDto.builder()
            .openingHours(storeOperationInfo.getOpeningHours())
            .breakTime(storeOperationInfo.getBreakTime())
            .holiday(storeOperationInfo.getHolidayDays())
            .holiday(storeOperationInfo.getHolidayNotice())
            .build();
  }
}

