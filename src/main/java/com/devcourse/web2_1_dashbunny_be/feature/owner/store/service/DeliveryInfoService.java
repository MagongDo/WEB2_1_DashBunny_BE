
package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.DeliveryAreaRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.DeliveryInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.DeliveryOperatingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryInfoService {

    private final DeliveryOperatingInfoRepository repository;

    // Retrieve Delivery Info
    @Transactional(readOnly = true)
    public DeliveryInfoResponseDto getDeliveryInfo() {
        DeliveryOperatingInfo info = repository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new IllegalArgumentException("No delivery info found"));

        return new DeliveryInfoResponseDto(
                info.getMinDeliveryTime(),
                info.getMaxDeliveryTime(),
                info.getDeliveryAreaInfo()
        );
    }

    // Set Delivery Area
    @Transactional
    public void setDeliveryArea(DeliveryAreaRequestDto requestDto) {
        DeliveryOperatingInfo existingEntity = repository.findFirstByOrderByIdDesc()
                .orElse(new DeliveryOperatingInfo());

        DeliveryOperatingInfo updatedEntity = requestDto.toEntity(existingEntity);
        repository.save(updatedEntity);
    }
}
