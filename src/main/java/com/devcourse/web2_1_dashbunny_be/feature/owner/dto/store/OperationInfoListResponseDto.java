package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 가게 운영 정보 조회를 위한 DTO.
 */
@Getter
@Builder
public class OperationInfoListResponseDto {
  private String openingHours;
  private String breakTime;
  private String holidayDays;
  private String holidayNotice;

  /**
   *엔티티를 디티오로 반환하는 메서드.
   */
  public static OperationInfoListResponseDto fromEntity(StoreOperationInfo storeOperationInfo) {
    return OperationInfoListResponseDto.builder()
            .openingHours(storeOperationInfo.getOpeningHours())
            .breakTime(storeOperationInfo.getBreakTime())
            .holidayDays(storeOperationInfo.getHolidayDays())
            .holidayNotice(storeOperationInfo.getHolidayNotice())
            .build();
  }
}

