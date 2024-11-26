package com.devcourse.web2_1_dashbunny_be.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.OrderInfoRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.OrderInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller.OrderInfoController;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.OrderInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderInfoController.class)
class OrderInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderInfoService orderInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetOrderInfo() throws Exception {
        // 서비스의 응답값 모킹 (Mock)
        OrderInfoResponseDto mockResponse = new OrderInfoResponseDto(
                "On", // 포장 여부
                1000L, // 포장 할인 금액
                5000L, // 최소 주문 금액
                1500L  // 기본 배달 팁
        );

        Mockito.when(orderInfoService.getOrderInfo()).thenReturn(mockResponse);

        // GET 요청 실행 및 응답 검증
        mockMvc.perform(get("/api/store/order-info")
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isTakeout").value("On"))
                .andExpect(jsonPath("$.takeoutDiscount").value(1000))
                .andExpect(jsonPath("$.minOrderAmount").value(5000))
                .andExpect(jsonPath("$.deliveryTip").value(1500));
    }

    @Test
    void testSaveOrUpdateOrderInfo() throws Exception {
        // 요청 데이터 (JSON Body) 모킹
        OrderInfoRequestDto mockRequest = new OrderInfoRequestDto();
        mockRequest.setTakeoutDiscount(2000L);
        mockRequest.setMinOrderAmount(6000L);
        mockRequest.setDeliveryTip(1800L);

        // 서비스 레이어의 동작을 모킹
        Mockito.doNothing().when(orderInfoService).saveOrUpdateOrderInfo(any(OrderInfoRequestDto.class));

        // PUT 요청 실행 및 응답 검증
        mockMvc.perform(put("/api/store/order-info")
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))) // 요청 데이터를 JSON으로 변환
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("주문 정보가 성공적으로 저장되었습니다."));
    }
}