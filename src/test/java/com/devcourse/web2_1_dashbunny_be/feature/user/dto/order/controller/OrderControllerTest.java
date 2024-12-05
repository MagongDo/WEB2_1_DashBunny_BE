/*
package com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.controller.dto.OrderInfoRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.CompletableFuture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    private static final String ERROR_TOPIC = "/topic/order/error";
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private OrderService orderService; // Mock된 OrderService

    @MockBean
    private SimpMessageSendingOperations messageTemplate; // Mock된 SimpMessagingTemplate

    @Test
    void creatOrder_success() {
        // Given: 성공적인 주문 생성 시나리오 설정
        OrderInfoRequestDto orderRequest = new OrderInfoRequestDto();
        Orders orderMock = new Orders();
        orderMock.setOrderId(12345L);

        when(orderService.creatOrder(orderRequest))
                .thenReturn(CompletableFuture.completedFuture(orderMock));

        // When: OrderController의 creatOrder 메서드 실행
        CompletableFuture<ResponseEntity<String>> response = new OrderController(orderService, messageTemplate)
                .creatOrder(orderRequest);

        // Then: 반환 값 검증
        ResponseEntity<String> result = response.join(); // CompletableFuture 결과를 동기적으로 기다림
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("주문 접수 요청에 성공했습니다.");

        // 메시지 발송 검증
        verify(messageTemplate).convertAndSend("/topic/order/12345", "주문 접수 중");
    }

    @Test
    void creatOrder_failure() {
        // Given: 예외 발생 시나리오 설정
        OrderInfoRequestDto orderRequest = new OrderInfoRequestDto();

        when(orderService.creatOrder(orderRequest))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("DB Error")));

        // When: OrderController의 creatOrder 메서드 실행
        CompletableFuture<ResponseEntity<String>> response = new OrderController(orderService, messageTemplate)
                .creatOrder(orderRequest);

        // Then: 반환 값 검증
        ResponseEntity<String> result = response.join();
        assertThat(result.getStatusCodeValue()).isEqualTo(500);
        assertThat(result.getBody()).contains("주문 처리 중 문제가 발생했습니다: DB Error");

        // 실패 메시지 발송 검증
        verify(messageTemplate).convertAndSend( ERROR_TOPIC,"주문 접수 실패");
    }
}
*/

