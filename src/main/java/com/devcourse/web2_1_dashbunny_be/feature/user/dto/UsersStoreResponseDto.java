package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersStoreResponseDto {
  private String storeId;                     // 가게 ID
  private String storeName;                 // 가게 이름
  private Double rating;                    // 평점
  private Integer reviewCount;              // 리뷰 수
  private Long deliveryTip;          // 기본 배달 팁
  private String minDeliveryTime;           // 최소 배달 예상 시간
  private String maxDeliveryTime;           // 최대 배달 예상 시간
  private String storeImage;                // 가게 이미지 URL
  private Long minimumOrderPrice;           // 최소 주문 금액
  private Long ownerCouponId;               // 쿠폰 ID
  private StoreStatus storeStatus;          // 가게 상태
  private List<UsersMenuGroupDto> usersMenuGroup;    // 메뉴 그룹 리스트
  private List<UsersMenuDto> usersMenus;              // 독립 메뉴 리스트

  public static UsersStoreResponseDto toStoreResponseDto(StoreManagement store, List<MenuGroup> menuGroup, List<MenuManagement> usersMenu, DeliveryOperatingInfo deliveryOperatingInfo) {

    return UsersStoreResponseDto.builder()
              .storeId(store.getStoreId())
              .storeName(store.getStoreName())
              .rating(store.getStoreFeedback().getRating())
              .reviewCount(store.getStoreFeedback().getReviewCount())
              .deliveryTip(deliveryOperatingInfo.getDeliveryTip())
              .minDeliveryTime(deliveryOperatingInfo.getMinDeliveryTime())
              .maxDeliveryTime(deliveryOperatingInfo.getMaxDeliveryTime())
              .storeImage(store.getStoreLogo())
              .minimumOrderPrice(deliveryOperatingInfo.getMinOrderAmount())
              .ownerCouponId(store.getCouponList() != null && !store.getCouponList().isEmpty()
                      ? store.getCouponList().get(0).getCouponId() : null)
              .storeStatus(store.getStoreStatus())
              .usersMenuGroup(menuGroup != null
                      ? menuGroup.stream()
                      .map(UsersMenuGroupDto::toMenuGroupDto)
                      .toList()
                      : null)
              .usersMenus(usersMenu != null
                      ? usersMenu.stream()
                      .filter(menu -> menuGroup == null) // 메뉴 그룹에 속하지 않은 메뉴만 선택
                      .map(UsersMenuDto::toMenuDto)
                      .toList()
                      : null)
              .build();
  }

}
