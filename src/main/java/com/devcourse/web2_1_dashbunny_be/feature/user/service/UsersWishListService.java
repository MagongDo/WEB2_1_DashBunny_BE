package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.WishList;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.DeliveryOperatingInfoRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersWishListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersWishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersWishListService {
  private final UserRepository userRepository;
  private final UsersWishListRepository usersWishListRepository;
  private final StoreManagementRepository storeManagementRepository;
  private final DeliveryOperatingInfoRepository deliveryOperatingInfoRepository;
  @Transactional
  public void getUsersWishModification(Long userId, String storeId) {
    WishList wishList = usersWishListRepository.findByStoreIdAndUserId(storeId, userId);
    StoreManagement store=storeManagementRepository.findById(storeId).orElseThrow(IllegalArgumentException::new);

    if (wishList == null) {
      // 위시리스트 추가
      wishList = WishList.builder()
              .userId(userId)
              .storeId(storeId)
              .build();

      usersWishListRepository.save(wishList);
      store.setWishCount(store.increaseWishCount());
    } else {
      // 위시리스트 제거
      usersWishListRepository.delete(wishList);
      store.setWishCount(store.decreaseWishCount());
    }
    // 변경된 가게 정보 저장
    storeManagementRepository.save(store);
  }

  @Transactional
  public List<UsersStoreListResponseDto> getUsersWishList(Long userId) {
    // 사용자 위시리스트 가져오기
    List<WishList> wishList = usersWishListRepository.findByUserId(userId);

    // 스토어 리스트 가져오기
    List<UsersStoreListResponseDto> storeList = wishList.stream()
            .map(wish -> {
              DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperatingInfoRepository.findByStoreId(wish.getStoreId());
              StoreManagement store = storeManagementRepository.findById(wish.getStoreId())
                      .orElseThrow(() -> new IllegalArgumentException("Store not found for ID: " + wish.getStoreId()));
              UsersStoreListResponseDto dto = new UsersStoreListResponseDto();
              return dto.toUsersStoreListResponseDto(store, deliveryOperatingInfo);
            })
            .collect(Collectors.toList());

    return storeList;
  }

}
