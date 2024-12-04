package com.devcourse.web2_1_dashbunny_be.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
  * 컨트롤러에서 동적 토픽 적용하여 사용하기.
   *  "/topic/orders" + orderId, "/topic/delivery" + deliveryId.
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic"); // 메시지 브로커 설정
    config.setApplicationDestinationPrefixes("/app"); // 애플리케이션 목적지 프리픽스
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws") // 엔드포인트 등록
         .setAllowedOrigins("*") // CORS 허용
         .withSockJS(); // SockJS 사용
    }
}
