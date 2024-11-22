package com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.DeliveryInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.SetDeliveryAreaRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.DeliveryInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 배달 정보 컨트롤러
 */
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class DeliveryInfoController {

    private final DeliveryInfoService deliveryInfoService;

    /**
     * 배달 정보를 조회합니다.
     */
    @GetMapping("/delivery-info")
    public ResponseEntity<DeliveryInfoResponseDto> getDeliveryInfo() {
        DeliveryInfoResponseDto deliveryInfo = deliveryInfoService.getDeliveryInfo();
        return ResponseEntity.ok(deliveryInfo);
    }

    /**
     * 배달 지역을 설정합니다.
     */
    @PostMapping("/delivery-area")
    public ResponseEntity<String> setDeliveryArea(@RequestBody SetDeliveryAreaRequestDto deliveryAreaRequest) {
        deliveryInfoService.setDeliveryArea(deliveryAreaRequest);
        return ResponseEntity.ok("배달 범위가 성공적으로 설정되었습니다.");
    }
}