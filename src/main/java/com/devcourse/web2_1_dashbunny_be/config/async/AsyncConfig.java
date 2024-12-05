package com.devcourse.web2_1_dashbunny_be.config.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 스레드 튜닝을 할 수 있는 class.
 * 초기화 (initialize)
 * ThreadPoolTaskExecutor를 커스텀하는 방식으로 사용을 했기 때문에 반드시 초기화를 진행해 주어야한다.
 */
@EnableAsync
@Configuration
public class AsyncConfig {

  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    executor.setCorePoolSize(10); // 기본 스레드 수
    executor.setMaxPoolSize(20); // 최대 스레드 수
    executor.setQueueCapacity(500); // 큐 용량
    executor.setThreadNamePrefix("Async-"); // 스레드 이름의 접두사
    executor.initialize();

    return executor;
  }
}

