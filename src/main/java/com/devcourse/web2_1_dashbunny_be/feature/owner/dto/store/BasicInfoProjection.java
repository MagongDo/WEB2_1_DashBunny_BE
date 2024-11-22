package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;

public interface BasicInfoProjection {

  /**
   * StoreMenaement
   */
  String getStoreName();
  String getContactNumber();
  StoreStatus getStoreStatus();
  String getAddress();
  String getStoreLogo();
  String getStoreBannerImage();
  String getDescription();
  String getShortsUrl();

}
