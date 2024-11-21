package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import lombok.Getter;

/**
 * 단건 환불 처리를 위한 DTO 클래스.
 */
@Getter
public class UpdateSoldOutRequestDto {
  private boolean soldOut;
}
