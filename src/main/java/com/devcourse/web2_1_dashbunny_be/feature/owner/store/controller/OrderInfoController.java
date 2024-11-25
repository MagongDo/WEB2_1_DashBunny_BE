/*
package com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.MessageResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.OrderInfoRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.OrderInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.OrderInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class OrderInfoController {

    private final OrderInfoService orderInfoService;

    // GET: Retrieve Order Info
    @GetMapping("/order-info")
    public ResponseEntity<OrderInfoResponseDto> getOrderInfo() {
        return ResponseEntity.ok(orderInfoService.getOrderInfo());
    }

    // PUT: Save or Update Order Info
    @PutMapping("/order-info")
    public ResponseEntity<MessageResponseDto> saveOrUpdateOrderInfo(@RequestBody OrderInfoRequestDto requestDto) {
        orderInfoService.saveOrUpdateOrderInfo(requestDto);
        return ResponseEntity.ok(new MessageResponseDto("주문 정보가 성공적으로 저장되었습니다."));
    }
}*/
