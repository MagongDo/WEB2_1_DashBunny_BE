package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.config.GeoUtils;
import com.devcourse.web2_1_dashbunny_be.config.KakaoGeocoding;
import com.devcourse.web2_1_dashbunny_be.config.RedisKeyUtil;
import com.devcourse.web2_1_dashbunny_be.domain.owner.*;
import com.devcourse.web2_1_dashbunny_be.domain.user.WishList;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.DeliveryOperatingInfoRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreFeedBackRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersWishListRepository;
import com.nimbusds.jose.shaded.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UsersStoreService {
  private final StoreManagementRepository storeManagementRepository;
  private final UserRepository userRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  private final DeliveryOperatingInfoRepository deliveryOperationInfoRepository;
  private final MenuGroupRepository menuGroupRepository;
  private final MenuRepository menuRepository;
  private final KakaoGeocoding kakaoGeocoding; // 추가된 필드
  private final UsersWishListRepository usersWishListRepository;
  private final StoreFeedBackRepository storeFeedBackRepository;

  // 가게 깃발의 반경을 조사하여 해당 가게의 사용자 배달 가능 여부를 판단하여 Redis에 저장
  public void redisAddStoreList(String userId, String address) {
    JsonObject addressLatLon = getUserAdressLatLon(address);

    // 사용자의 위도와 경도 추출
    double userLatitude = addressLatLon.get("latitude").getAsDouble();
    double userLongitude = addressLatLon.get("longitude").getAsDouble();

    // 가게 목록 가져오기
    List<StoreManagement> stores = usersStoreList(address);

    // 배달 가능한 가게 ID 리스트
    List<String> deliverableStoreIds = new ArrayList<>();

    // 가게들에 대해 배달 가능 여부 판별
    for (StoreManagement store : stores) {
      for (StoreFlag storeFlag : store.getStoreFlags()) {
        double flagLatitude = storeFlag.getLatitude();
        double flagLongitude = storeFlag.getLongitude();

        // 사용자가 배달 반경에 포함되는지 확인
        double distance = GeoUtils.getUsersWithinRadius(
                flagLatitude, flagLongitude, userLatitude, userLongitude
        );

        // 반경 내 사용자라면 가게 ID 추가
        if (distance <= 6.5) { // 예시로 1.5km로 수정
          deliverableStoreIds.add(store.getStoreId());
          break; // 한 플래그라도 포함되면 해당 가게를 배달 가능으로 처리
        }
      }
    }
    log.info("deliverableStoreIds: {}", deliverableStoreIds);
    String redisKey = RedisKeyUtil.generateKey(userId, userLatitude, userLongitude);

    // Redis에 저장
    redisTemplate.opsForValue().set(redisKey, deliverableStoreIds);
  }

  // 사용자 주소에 따른 반경 6.5km 이내에 있는 가게 리스트 저장
  public List<StoreManagement> usersStoreList(String address) {
    List<StoreManagement> filteredStores = new ArrayList<>();
    List<StoreManagement> stores = storeManagementRepository.findAll();
    JsonObject addressLatLon = getUserAdressLatLon(address);
    double userLatitude = addressLatLon.get("latitude").getAsDouble();
    double userLongitude = addressLatLon.get("longitude").getAsDouble();
    for (StoreManagement store : stores) {
      double distance = GeoUtils.getUsersWithinRadius(userLatitude, userLongitude, store.getLatitude(), store.getLongitude());
      if (distance <= 6.5) {
        filteredStores.add(store);
      }
    }
    log.info("filtered stores: {}", filteredStores);
    return filteredStores;
  }

  // 레디스에 저장된 데이터를 활용하여 카테고리별 가게 리스트 반환
  public List<UsersStoreListResponseDto> usersStoreListResponse(String userId, String address, String category) {
    List<UsersStoreListResponseDto> responseDtos = new ArrayList<>();
    JsonObject addressLatLon = getUserAdressLatLon(address);
    double userLatitude = addressLatLon.get("latitude").getAsDouble();
    double userLongitude = addressLatLon.get("longitude").getAsDouble();

    // Redis 키 생성 (소수점 이하 6자리 포맷)
    String redisKey = RedisKeyUtil.generateKey(userId, userLatitude, userLongitude);

    // Redis에서 데이터 확인
    if (!checkRedisData(userId, address)) {
      // Redis 키가 없으면 데이터를 새로 추가
      redisAddStoreList(userId, address);
    }

    // Redis에서 가게 ID 리스트 가져오기
    List<String> storeIds = (List<String>) redisTemplate.opsForValue().get(redisKey);
    log.info("storeIds: {}", storeIds);
    if (storeIds == null || storeIds.isEmpty()) {
      return responseDtos; // Redis에 저장된 데이터가 비어 있으면 빈 리스트 반환
    }

    // 가게 ID로 데이터베이스에서 가게 정보 가져오기 및 필터링
    for (String storeId : storeIds) {
      StoreManagement store = storeManagementRepository.findById(storeId)
             .orElse(null);
      if (store == null) {
        continue; // 가게 정보가 없으면 무시
      }

      // 카테고리 필터링
      boolean hasCategory = store.getCategory().stream()
             .anyMatch(cat -> cat.getCategoryType().name().equalsIgnoreCase(category));
      if (!hasCategory) {
        continue; // 카테고리가 일치하지 않으면 무시
      }

      // DTO로 변환하여 응답 리스트에 추가
      StoreFeedBack storeFeedBack = storeFeedBackRepository.findByStoreId(storeId);
      DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperationInfoRepository.findByStoreId(storeId);
      UsersStoreListResponseDto dto = UsersStoreListResponseDto.toUsersStoreListResponseDto(store, deliveryOperatingInfo, storeFeedBack);
      responseDtos.add(dto);
    }
    return responseDtos;
  }

  // 사용자의 레디스 키 유무 확인
  public Boolean checkRedisData(String userId, String address) {
    JsonObject addressLatLon = getUserAdressLatLon(address);
    double userLatitude = addressLatLon.get("latitude").getAsDouble();
    double userLongitude = addressLatLon.get("longitude").getAsDouble();
    String redisKey = RedisKeyUtil.generateKey(userId, userLatitude, userLongitude);
    boolean hasKey = Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
    log.info("Redis check result: {}", redisTemplate.opsForValue().get(redisKey));
    log.info("Redis Key: {}", redisKey);
    return hasKey;
  }

  public JsonObject getUserAdressLatLon(String address) {
    try {
      // 사용자의 주소를 통해 좌표를 가져옴
      return kakaoGeocoding.getCoordinatesFromAddress(address);
    } catch (Exception e) {
      throw new RuntimeException("Failed to get coordinates from address", e);
    }
  }
  public UsersStoreResponseDto getStoreDetails(Long userId, String storeId) {
    List<MenuGroup> menuGroup = menuGroupRepository.findByStoreId(storeId);
    StoreManagement store = storeManagementRepository.findById(storeId).orElseThrow(IllegalArgumentException::new);
    List<MenuManagement> menu = menuRepository.findAllByStoreId(storeId);
    DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperationInfoRepository.findByStoreId(storeId);
    WishList wishList = usersWishListRepository.findByStoreIdAndUserId(storeId, userId);
    StoreFeedBack storeFeedBack = storeFeedBackRepository.findByStoreId(storeId);
    Boolean wishStatus;
    wishStatus = wishList != null;

    return UsersStoreResponseDto.toStoreResponseDto(store, storeFeedBack, menuGroup, menu, deliveryOperatingInfo, wishStatus);
  }

  // 레디스에 저장된 데이터를 활용하여 주위 모든 가게 리스트 반환
  public List<UsersStoreListResponseDto> getNearbyStores(String userId, String address) {
    List<UsersStoreListResponseDto> responseDtos = new ArrayList<>();
    // 사용자의 주소를 기반으로 좌표를 가져옴
    JsonObject addressLatLon;
    try {
      addressLatLon = kakaoGeocoding.getCoordinatesFromAddress(address);
    } catch (Exception e) {
      throw new RuntimeException("Failed to get coordinates from address", e);
    }

    if (addressLatLon == null) {
      throw new RuntimeException("Failed to get coordinates from address");
    }
    // 사용자의 위도와 경도 추출
    double userLatitude = addressLatLon.get("latitude").getAsDouble();
    double userLongitude = addressLatLon.get("longitude").getAsDouble();

    // Redis 키 생성 (소수점 이하 6자리 포맷)
    String redisKey = RedisKeyUtil.generateKey(userId, userLatitude, userLongitude);
    // Redis에서 데이터 확인
    if (!checkRedisData(userId, address)) {
      // Redis 키가 없으면 데이터를 새로 추가
      redisAddStoreList(userId, address);
    }

    // Redis에서 가게 ID 리스트 가져오기
    @SuppressWarnings("unchecked")
    List<String> storeIds = (List<String>) redisTemplate.opsForValue().get(redisKey);

    if (storeIds == null || storeIds.isEmpty()) {
      return responseDtos; // Redis에 저장된 데이터가 비어 있으면 빈 리스트 반환
    }

    // 가게 ID로 데이터베이스에서 가게 정보 가져오기 및 필터링
    for (String storeId : storeIds) {
      StoreManagement store = storeManagementRepository.findById(storeId)
              .orElse(null);
      if (store == null) {
        continue; // 가게 정보가 없으면 무시
      }

      // DTO로 변환하여 응답 리스트에 추가
      StoreFeedBack storeFeedBack = storeFeedBackRepository.findByStoreId(storeId);
      DeliveryOperatingInfo deliveryOperatingInfo=deliveryOperationInfoRepository.findByStoreId(storeId);
      UsersStoreListResponseDto dto = new UsersStoreListResponseDto();
      dto.toUsersStoreListResponseDto(store,deliveryOperatingInfo,storeFeedBack);
      responseDtos.add(dto);
    }
    return responseDtos;
  }

}
