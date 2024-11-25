/*
package com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.DeliveryAreaRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.DeliveryInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.MessageResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.DeliveryInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class DeliveryInfoController {

    private final DeliveryInfoService deliveryInfoService;

    // GET: Retrieve Delivery Info
    @GetMapping("/delivery-info")
    public ResponseEntity<DeliveryInfoResponseDto> getDeliveryInfo() {
        return ResponseEntity.ok(deliveryInfoService.getDeliveryInfo());
    }

    // POST: Set Delivery Area
    @PostMapping("/delivery-area")
    public ResponseEntity<MessageResponseDto> setDeliveryArea(@RequestBody DeliveryAreaRequestDto requestDto) {
        deliveryInfoService.setDeliveryArea(requestDto);
        return ResponseEntity.ok(new MessageResponseDto("배달 범위가 성공적으로 설정되었습니다."));
    }
}*/
