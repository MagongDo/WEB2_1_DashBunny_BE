package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class IdempotencyKeyService {

  private final RedisTemplate<String, String> redisTemplate;

  public void saveIdempotencyKey(String idempotencyKey, List<String> menuNames, long ttl) {
      String concatenatedMenuNames = String.join(", ", menuNames); // 메뉴 이름을 합침
      redisTemplate.opsForValue().set(idempotencyKey, concatenatedMenuNames, ttl, TimeUnit.SECONDS);
  }

  public String getIdempotencyKey(String idempotencyKey) {
      return redisTemplate.opsForValue().get(idempotencyKey);
  }
}