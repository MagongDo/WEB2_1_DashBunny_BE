package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class IdempotencyKeyService {

  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;


  private String generateMappingKey(String userId, List<String> menuNames) {
    List<String> sortedMenuNames = menuNames.stream()
              .sorted()
              .toList();
    String menus = String.join(",", sortedMenuNames);
    return userId + ":" + menus;
  }

  public String getOrCreateIdempotencyKey(String userId, List<String> menuNames) {
    String mappingKey = generateMappingKey(userId, menuNames);
    String existingUuid = redisTemplate.opsForValue().get(mappingKey);
    if (existingUuid != null) {
      return existingUuid;
    } else {
      String newUuid = UUID.randomUUID().toString();
      // 매핑 키에 UUID 저장 (TTL 설정)
      redisTemplate.opsForValue().set(mappingKey, newUuid, 900L, TimeUnit.SECONDS);
      return newUuid;
    }
  }

}
