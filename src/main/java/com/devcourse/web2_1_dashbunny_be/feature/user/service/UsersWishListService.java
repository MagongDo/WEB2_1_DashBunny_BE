package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreFeedBack;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.WishList;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.DeliveryOperatingInfoRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreFeedBackRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersWishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UsersWishListService {
  private final UserRepository userRepository;
  private final UsersWishListRepository usersWishListRepository;
  private final StoreManagementRepository storeManagementRepository;
  private final DeliveryOperatingInfoRepository deliveryOperatingInfoRepository;
  private final StoreFeedBackRepository storeFeedBackRepository;
  @Transactional
  public void getUsersWishModification(Long userId, String storeId) {
    WishList wishList = usersWishListRepository.findByStoreIdAndUserId(storeId, userId);
    StoreFeedBack storeFeedBack = storeFeedBackRepository.findByStoreId(storeId);

    if (wishList == null) {
      // 위시리스트 추가
      wishList = WishList.builder()
              .userId(userId)
              .storeId(storeId)
              .build();
      usersWishListRepository.save(wishList);
      storeFeedBack.setWishlistCount(storeFeedBack.increaseWishCount());
    } else {
      // 위시리스트 제거
      usersWishListRepository.delete(wishList);
      storeFeedBack.setWishlistCount(storeFeedBack.decreaseWishCount());
    }
    // 변경된 가게 정보 저장
    storeFeedBackRepository.save(storeFeedBack);
  }

  @Transactional
  public List<UsersStoreListResponseDto> getUsersWishList(Long userId) {
    // 사용자 위시리스트 가져오기
    List<WishList> wishList = usersWishListRepository.findByUserId(userId);
    log.info("WishList size: " + wishList.size());
    // 스토어 리스트 가져오기
    List<UsersStoreListResponseDto> storeList = wishList.stream()
            .map(wish -> {
              DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperatingInfoRepository.findByStoreId(wish.getStoreId());
              StoreManagement store = storeManagementRepository.findById(wish.getStoreId())
                      .orElseThrow(() -> new IllegalArgumentException("Store not found for ID: " + wish.getStoreId()));
              StoreFeedBack storeFeedBack = storeFeedBackRepository.findByStoreId(wish.getStoreId());
              return UsersStoreListResponseDto.toUsersStoreListResponseDto(store, deliveryOperatingInfo, storeFeedBack);
            })
            .collect(Collectors.toList());

    return storeList;
  }

}
