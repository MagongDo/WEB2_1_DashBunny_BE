package com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.OrderInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.UpdateOrderInfoRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.OrderInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 주문 정보 컨트롤러
 */
@RestController
@RequestMapping("/api/store/order-info")
@RequiredArgsConstructor
public class OrderInfoController {

    private final OrderInfoService orderInfoService;

    /**
     * 주문 정보를 조회합니다.
     */
    @GetMapping
    public ResponseEntity<OrderInfoResponseDto> getOrderInfo() {
        OrderInfoResponseDto orderInfo = orderInfoService.getOrderInfo();
        return ResponseEntity.ok(orderInfo);
    }

    /**
     * 주문 정보를 저장 또는 수정합니다.
     */
    @PutMapping
    public ResponseEntity<String> updateOrderInfo(@RequestBody UpdateOrderInfoRequestDto updateOrderInfo) {
        orderInfoService.updateOrderInfo(updateOrderInfo);
        return ResponseEntity.ok("주문 정보가 성공적으로 저장되었습니다.");
    }
}