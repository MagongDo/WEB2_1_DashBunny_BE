package com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 관리자의 정보를 담는 DTO.
 */
@Getter
@AllArgsConstructor
public class AdminInfoResponseDto {
  private String name;
  private String role;
}
