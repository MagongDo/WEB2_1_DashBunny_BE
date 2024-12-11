package com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가게 폐업신청 시 가게 상태 변경  Dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreClosureRequestDto {
  private StoreStatus storeStatus; //가게 상태
}
