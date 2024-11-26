package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersStoreResponseDto {

    private String storeId;                     // 가게 ID
    private String storeName;                 // 가게 이름
    private Double rating;                    // 평점
    private Integer reviewCount;              // 리뷰 수
    private Long defaultDeliveryTip;          // 기본 배달 팁
    private String minDeliveryTime;           // 최소 배달 예상 시간
    private String maxDeliveryTime;           // 최대 배달 예상 시간
    private String storeImage;                // 가게 이미지 URL
    private Long minimumOrderPrice;           // 최소 주문 금액
    private Long ownerCouponId;               // 쿠폰 ID
    private StoreStatus storeStatus;          // 가게 상태
    private List<UsersMenuGroupDto> usersMenuGroup;    // 메뉴 그룹 리스트
    private List<UsersMenuDto> usersMenus;              // 독립 메뉴 리스트

    public static UsersStoreResponseDto toStoreResponseDto(StoreManagement store) {
        return UsersStoreResponseDto.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .rating(store.getStoreFeedback().getRating())
                .reviewCount(store.getStoreFeedback().getReviewCount())
                .defaultDeliveryTip(store.getDeliveryInfo().getBaseDeliveryTip())
                .minDeliveryTime(store.getDeliveryInfo().getMinDeliveryTime())
                .maxDeliveryTime(store.getDeliveryInfo().getMaxDeliveryTime())
                .storeImage(store.getStoreLogo())
                .minimumOrderPrice(store.getDeliveryInfo().getMinOrderAmount())
                .ownerCouponId(store.getOwnerCoupon() != null && !store.getOwnerCoupon().isEmpty()
                        ? store.getOwnerCoupon().get(0).getCouponId() : null)
                .storeStatus(store.getStoreStatus())
                .usersMenuGroup(store.getMenuGroup() != null
                        ? store.getMenuGroup().stream()
                        .map(UsersMenuGroupDto::toMenuGroupDto)
                        .toList()
                        : null)
                .usersMenus(store.getMenuManagements() != null
                        ? store.getMenuManagements().stream()
                        .filter(menu -> menu.getMenuGroup() == null) // 메뉴 그룹에 속하지 않은 메뉴만 선택
                        .map(UsersMenuDto::toMenuDto)
                        .toList()
                        : null)
                .build();
    }

}
