/*
package com.devcourse.web2_1_dashbunny_be.config;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisComponent {

  final RedisTemplate<String, String> redisTemplate;
  final JsonComponent jsonComponent;

  public <T> T getObjectByKey(String key, Class<T> clazz) {
    ValueOperations<String, String> vop = redisTemplate.opsForValue();
    String jsonString = vop.get(key);
    if (StringUtils.isNotEmpty(jsonString)) {
      return jsonComponent.jsonToObject(jsonString, clazz);
    } else {
      return null;
    }
  }

  public void setObjectByKey(String key, Object obj) {
    ValueOperations<String, String> vop = redisTemplate.opsForValue();
    vop.set(key, jsonComponent.objectToJson(obj));
    redisTemplate.expire(key, 7, TimeUnit.DAYS);
  }
}

*/
