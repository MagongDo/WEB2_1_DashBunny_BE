/*
package com.devcourse.web2_1_dashbunny_be.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.DeliveryAreaRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.DeliveryInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller.DeliveryInfoController;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.DeliveryInfoService;
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

@WebMvcTest(DeliveryInfoController.class)
class DeliveryInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryInfoService deliveryInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDeliveryInfo() throws Exception {
        // Mock the response from the service
        DeliveryInfoResponseDto mockResponse = new DeliveryInfoResponseDto(
                "30 minutes",
                "60 minutes",
                "Area A, Area B"
        );

        Mockito.when(deliveryInfoService.getDeliveryInfo()).thenReturn(mockResponse);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/store/delivery-info")
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.minDeliveryTime").value("30 minutes"))
                .andExpect(jsonPath("$.maxDeliveryTime").value("60 minutes"))
                .andExpect(jsonPath("$.deliveryAreaInfo").value("Area A, Area B"));
    }

    @Test
    void testSetDeliveryArea() throws Exception {
        // Mock the request body
        DeliveryAreaRequestDto mockRequest = new DeliveryAreaRequestDto();
        mockRequest.setDeliveryRange("Area C, Area D");

        // Mock the service call (no return value for this service method)
        Mockito.doNothing().when(deliveryInfoService).setDeliveryArea(any(DeliveryAreaRequestDto.class));

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/store/delivery-area")
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("배달 범위가 성공적으로 설정되었습니다."));
    }
}*/
