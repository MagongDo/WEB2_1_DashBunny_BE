package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메뉴 이미지 수정을 위한 DTO 클래스.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMenuImageRequestDto {
  private String imageUrl; // 이미지 URL
}