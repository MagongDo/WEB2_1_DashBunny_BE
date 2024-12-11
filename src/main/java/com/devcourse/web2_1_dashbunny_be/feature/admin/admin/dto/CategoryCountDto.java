package com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryCountDto {
  private int korean;
  private int japanese;
  private int western;
  private int chinese;
  private int asian;
  private int lateNightFood;
  private int koreanSnacks;
  private int chicken;
  private int pizza;
  private int barbecue;
  private int cafeDessert;
  private int fastFood;
}
