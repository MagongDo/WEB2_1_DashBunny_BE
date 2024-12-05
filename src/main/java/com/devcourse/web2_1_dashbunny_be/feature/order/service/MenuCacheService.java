package com.devcourse.web2_1_dashbunny_be.feature.order.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MenuRepository menuRepository;
    private final Validator validator;

    private static final String STORE_KEY_PREFIX = "store:";

    /**
     * 특정 가게의 메뉴를 Redis에서 조회합니다.
     */
    public Map<Long, MenuManagement> getMenusByStore(String storeId) {
        String key = STORE_KEY_PREFIX + storeId;
        Map<Object, Object> menuMap = redisTemplate.opsForHash().entries(key);

        if (menuMap.isEmpty()) {
            return fetchAndCacheMenusByStore(storeId, key);
        }

        // Redis에서 메뉴 정보를 Map<Long, MenuManagement>로 변환
        return menuMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Long.parseLong((String) entry.getKey()),
                        entry -> (MenuManagement) entry.getValue()
                ));
    }

    /**
     * 특정 가게의 메뉴를 DB에서 조회 후 Redis에 저장합니다.
     */
    private Map<Long, MenuManagement> fetchAndCacheMenusByStore(String storeId, String key) {
        List<MenuManagement> menus = menuRepository.findAllByStoreId(storeId);

        if (menus.isEmpty()) {
            throw new IllegalArgumentException("해당 가게에 메뉴가 존재하지 않습니다. Store ID: " + storeId);
        }

        Map<String, MenuManagement> menuMap = menus.stream()
                .collect(Collectors.toMap(
                        menu -> String.valueOf(menu.getMenuId()),
                        menu -> menu
                ));

        redisTemplate.opsForHash().putAll(key, menuMap);
        redisTemplate.expire(key, Duration.ofHours(24)); // TTL 설정

        return menuMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Long.parseLong(entry.getKey()),
                        Map.Entry::getValue
                ));
    }

    /**
     * 특정 가게의 메뉴에 대해 개별 메뉴 업데이트.
     */
    public void updateMenuInStore(String storeId, MenuManagement menu) {
        String key = STORE_KEY_PREFIX + storeId;
        redisTemplate.opsForHash().put(key, String.valueOf(menu.getMenuId()), menu);
        log.info("메뉴 업데이트 완료 - Store ID: {}, Menu ID: {}", storeId, menu.getMenuId());
    }

    /**
     * 특정 가게의 메뉴 삭제.
     */
    public void deleteMenuFromStore(String storeId, Long menuId) {
        String key = STORE_KEY_PREFIX + storeId;
        redisTemplate.opsForHash().delete(key, String.valueOf(menuId));
        log.info("메뉴 삭제 완료 - Store ID: {}, Menu ID: {}", storeId, menuId);
    }
}