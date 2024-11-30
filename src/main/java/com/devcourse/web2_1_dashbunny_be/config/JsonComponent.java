package com.devcourse.web2_1_dashbunny_be.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JsonComponent {

  private ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
      objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  public String objectToJson(Object obj) {
      String jsonStr = "";
      try {
          jsonStr = objectMapper.writeValueAsString(obj);
      } catch (Exception e) {
          log.error(ExceptionUtils.getStackTrace(e));
          new RuntimeException("ParsingException", e);
      }
      return jsonStr;
  }

  public <T> T jsonToObject(String json, Class<T> clazz) {
      T obj = null;
      try {
          obj = objectMapper.readValue(json, clazz);
      } catch (Exception e) {
          log.error(ExceptionUtils.getStackTrace(e));
          new RuntimeException("ConvertException", e);
      }
      return obj;
    }
}