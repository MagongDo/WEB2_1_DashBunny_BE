package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class UsersWishListResponseDto {
  private Long userId;
  private String storeId;
}
