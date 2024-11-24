package com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store;

import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;

/**
 * 가게 기본 정보를 조회하기 위한 Projection 인터페이스.
 * </p>
 * 필요한 필드만 선택적으로 조회하여
 * 쿼리 성능을 최적화
 * </p>
 * StoreManagement와 StoreOperationInfo 엔티티의 쇼츠 url 필드만 결합.
 */
public interface BasicInfoProjection {

  /**
   * StoreMenagement.
   */
  String getStoreName();

  /**
   * StoreMenagement.
   */
  String getContactNumber();

  /**
   * StoreMenagement.
   */
  StoreStatus getStoreStatus();

  /**
   * StoreMenagement.
   */
  String getAddress();

  /**
   * StoreMenagement.
   */
  String getStoreLogo();

  /**
   * StoreMenagement.
   */
  String getStoreBannerImage();

  /**
   * StoreMenagement.
   */
  String getStoreDescription();

  /**
   * StoreMenagement.
   */
  String getShortsUrl();

}
