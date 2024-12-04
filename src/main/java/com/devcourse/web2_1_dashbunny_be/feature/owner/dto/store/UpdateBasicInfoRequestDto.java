package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.Builder;
import lombok.Getter;

/**
 * 가게 기본정보 수정을 위한 DTO 클래스.
 */
@Getter
public class UpdateBasicInfoRequestDto {
  private String contactNumber;
  private String storeDescription;
  private String shortsUrl;
  private String shortsMenu;
  }
