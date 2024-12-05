package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShortsRequestDto {

  @NotBlank(message = "userId는 필수입니다.")
  private Long userId;  // 사용자 ID
  @NotBlank(message = "사용자의 주소는 필수입니다.")
  private String address; // 사용자의 주소
}
