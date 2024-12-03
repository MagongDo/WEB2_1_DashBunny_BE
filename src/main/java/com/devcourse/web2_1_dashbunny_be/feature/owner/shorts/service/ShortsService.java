package com.devcourse.web2_1_dashbunny_be.feature.owner.shorts.service;


import com.devcourse.web2_1_dashbunny_be.config.KakaoGeocoding;
import com.devcourse.web2_1_dashbunny_be.config.RedisKeyUtil;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreFeedBack;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreOperationInfo;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts.ShortsCreateRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts.ShortsRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreFeedBackRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreOperationInfoRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersStoreService;
import com.nimbusds.jose.shaded.gson.JsonObject;
import java.util.List;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ShortsService {
  private final StoreManagementRepository storeManagementRepository;
  private final StoreOperationInfoRepository storeOperationInfoRepository;
  private final MenuRepository menuRepository;
  private final UserRepository userRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  private final KakaoGeocoding kakaoGeocoding; // 추가된 필드
  private final StoreFeedBackRepository storeFeedBackRepository;
  private final UsersStoreService usersStoreService;

  /**
   * 쇼츠 url 저장
   *
   * @param requestDto 쇼츠 URL, storeID, menuId가 포함된 Dto
   * @return 저장결과
   */
  public StoreOperationInfo createShorts(ShortsCreateRequestDto requestDto) {
    // StoreManagement 및 MenuManagement 조회
    StoreManagement storeId = storeManagementRepository.findById(requestDto.getStoreId())
        .orElseThrow(() -> new IllegalArgumentException("잘못된 storeId: " + requestDto.getStoreId()));

    MenuManagement menuId = menuRepository.findById(requestDto.getMenuId())
        .orElseThrow(() -> new IllegalArgumentException("잘못된 menuId: " + requestDto.getMenuId()));

    // Shorts 엔티티 생성
    StoreOperationInfo shorts = StoreOperationInfo.builder()
        .shortsUrl(requestDto.getUrl())
        .store(storeId)
        .menuId(menuId)
        .build();

    // 저장
    return storeOperationInfoRepository.save(shorts);
  }

  // 레디스에 저장된 데이터를 활용하여 주위 모든 가게 리스트 반환
  public List<UsersStoreListResponseDto> getNearbyStoresShorts(ShortsRequestDto shortsRequestDto) {
    List<UsersStoreListResponseDto> responseDtos = new ArrayList<>();
    String userId = shortsRequestDto.getUserId();
    String address = shortsRequestDto.getAddress();
    // 사용자의 주소를 기반으로 좌표를 가져옴
    JsonObject addressLatLon;
    try {
      addressLatLon = kakaoGeocoding.getCoordinatesFromAddress(address);
      log.info("getNearbyStoresShorts addressLatLon: {} ", addressLatLon);
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
    log.debug("Generated Redis Key: {}", redisKey);
    // Redis에서 데이터 확인
    if (!usersStoreService.checkRedisData(userId, address)) {
      // Redis 키가 없으면 데이터를 새로 추가
      usersStoreService.redisAddStoreList(userId, address);
    }

    // Redis에서 가게 ID 리스트 가져오기
    @SuppressWarnings("unchecked")
    List<String> storeIds = (List<String>) redisTemplate.opsForValue().get(redisKey);

    if (storeIds == null || storeIds.isEmpty()) {
      log.info("체크 storeIds == null");
      return responseDtos; // Redis에 저장된 데이터가 비어 있으면 빈 리스트 반환
    }
    log.info("체크 storeIds != null");
    // 가게 ID로 데이터베이스에서 가게 정보 가져오기 및 필터링
    for (String storeId : storeIds) {
      StoreManagement store = storeManagementRepository.findById(storeId)
            .orElse(null);
      StoreFeedBack storeFeedBack = storeFeedBackRepository.findByStoreId(storeId);
      StoreOperationInfo storeOperationInfo = storeOperationInfoRepository.findByStore(store);
      if (store == null) {
        continue; // 가게 정보가 없으면 무시
      }
      // DTO로 변환하여 응답 리스트에 추가
      UsersStoreListResponseDto dto = new UsersStoreListResponseDto();
      dto.toUsersStoreShortsListResponseDto(store, storeOperationInfo, storeFeedBack);
      responseDtos.add(dto);
    }
    log.info("getNearbyStoresShorts : {}", responseDtos);
    return responseDtos;
  }

}
