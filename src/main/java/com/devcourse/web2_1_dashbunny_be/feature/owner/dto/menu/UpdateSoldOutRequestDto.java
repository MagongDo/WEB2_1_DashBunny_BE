package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 단건 품절 처리 요청 DTO 클래스.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSoldOutRequestDto {
  private Boolean isSoldOut; // 품절 여부 (true/false)
}