package com.devcourse.web2_1_dashbunny_be.feature.owner.store.service;


import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.DeliveryInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.delivery.SetDeliveryAreaRequestDto;
import org.springframework.stereotype.Service;

@Service
public class DeliveryInfoService {

    public DeliveryInfoResponseDto getDeliveryInfo() {
        return new DeliveryInfoResponseDto("30분", "1시간", "서울 강남구, 서초구");
    }

    public void setDeliveryArea(SetDeliveryAreaRequestDto deliveryAreaRequest) {
    }
}