/*
package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.OrderInfoRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.order.OrderInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.DeliveryOperatingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderInfoService {

    private final DeliveryOperatingInfoRepository repository;

    // Retrieve Order Info
    @Transactional(readOnly = true)
    public OrderInfoResponseDto getOrderInfo() {
        DeliveryOperatingInfo info = repository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new IllegalArgumentException("No order info found"));

        return new OrderInfoResponseDto(
                info.getIsTakeout() ? "On" : "Off",
                info.getTakeoutDiscount(),
                info.getMinOrderAmount(),
                info.getDeliveryTip()
        );
    }

    // Save or Update Order Info
    @Transactional
    public void saveOrUpdateOrderInfo(OrderInfoRequestDto requestDto) {
        DeliveryOperatingInfo existingEntity = repository.findFirstByOrderByIdDesc()
                .orElse(new DeliveryOperatingInfo());

        DeliveryOperatingInfo updatedEntity = requestDto.toEntity(existingEntity);
        repository.save(updatedEntity);
    }
}*/
