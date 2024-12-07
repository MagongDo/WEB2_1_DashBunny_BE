package com.devcourse.web2_1_dashbunny_be.feature.order.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuCacheService {

  @Qualifier("menuRedisTemplate")
  private final RedisTemplate<String, MenuManagement> menuRedisTemplate;
  private final MenuRepository menuRepository;
  private static final String STORE_KEY_PREFIX = "store:";

  /**
  * 특정 가게의 메뉴를 Redis에서 조회하고, 없는 경우 DB에서 가져와 캐싱합니다.
  * key: store:{storeId}
  * field: 각 메뉴 ID (101, 102, 103)
  * value: 각 메뉴의 객체 (MenuManagement)
  */
  public Map<Long, MenuManagement> getMenusByStore(String storeId, List<Long> menuIds) {
    String key = STORE_KEY_PREFIX + storeId;

    List<String> menuId = menuIds.stream().map(String::valueOf).toList();
    // 스토어 key, 각 메뉴 아이기 필드에 해당하는 밸류(단일 메뉴 객체)가 담긴  meueList 생성
    List<Object> meueList = menuRedisTemplate.opsForHash().multiGet(key, Collections.singleton(menuId));

    Map<Long, MenuManagement> menuCache = new HashMap<>();
    List<Long> missingMenuIds = new ArrayList<>();

    for (int i = 0; i < menuIds.size(); i++) {
      Object redisResult = meueList.get(i);
      if (redisResult != null) {
        menuCache.put(menuIds.get(i), (MenuManagement) redisResult);
      } else {
        missingMenuIds.add((Long) menuIds.get(i));
      }
        }

        // Redis에 없는 메뉴를 DB에서 조회하고 캐싱
        if (!missingMenuIds.isEmpty()) {
            log.info("Redis에 없는 메뉴 ID를 DB에서 조회 중: {}", missingMenuIds);
            Map<Long, MenuManagement> missingMenus = fetchAndCacheMenusByStore(storeId, key);
            menuCache.putAll(missingMenus);
        }

        return menuCache;
    }

    /**
     * 특정 가게의 메뉴를 DB에서 조회 후 Redis에 저장합니다.
     */
    private Map<Long, MenuManagement> fetchAndCacheMenusByStore(String storeId, String key) {
        List<MenuManagement> menus = menuRepository.findAllByStoreId(storeId);

        // Redis 캐싱 데이터 생성
        Map<String, MenuManagement> menuMap = menus.stream()
                .collect(Collectors.toMap(
                        menu -> String.valueOf(menu.getMenuId()),
                        menu -> menu
                ));

        // Redis에 데이터 저장
        try {
            menuRedisTemplate.opsForHash().putAll(key, menuMap);
            menuRedisTemplate.expire(key, Duration.ofHours(24));

            // 가게 엔트리에 등록
            menus.forEach(menu -> {
                String storeMenuKey = STORE_KEY_PREFIX + storeId;
                menuRedisTemplate.opsForHash().put(storeMenuKey, String.valueOf(menu.getMenuId()), menu);
            });

        } catch (Exception e) {
            log.error("Redis 캐싱 중 오류 발생: storeId = {}", storeId, e);
        }

        // 반환할 Map 생성
        return menuMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Long.parseLong(entry.getKey()), // 키를 Long으로 변환
                        Map.Entry::getValue                     // 값 유지
                ));
    }

    /**
     * 특정 가게의 메뉴에 대해 개별 메뉴 업데이트.
     */
    public void updateMenuInStore(String storeId, MenuManagement menu) {
        String key = STORE_KEY_PREFIX + storeId;
        menuRedisTemplate.opsForHash().put(key, String.valueOf(menu.getMenuId()), menu);
        log.info("메뉴 업데이트 완료 - Store ID: {}, Menu ID: {}", storeId, menu.getMenuId());
    }

    /**
     * 특정 가게의 메뉴 삭제.
     */
    public void deleteMenuFromStore(String storeId, Long menuId) {
        String key = STORE_KEY_PREFIX + storeId;
        menuRedisTemplate.opsForHash().delete(key, String.valueOf(menuId));
        log.info("메뉴 삭제 완료 - Store ID: {}, Menu ID: {}", storeId, menuId);
    }
}