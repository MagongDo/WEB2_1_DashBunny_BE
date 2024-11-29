package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DeliveryOperatingInfo")
public class DeliveryOperatingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryInfoId", nullable = false)
    private Long id;

    @Column(name = "storeId", nullable = false)
    private String storeId;


    // 배달 정보 조회 API, URL: /api/store/delivery-info
    @Column(name = "minDeliveryTime", length = 50)
    private String minDeliveryTime;

    @Column(name = "maxDeliveryTime", length = 50)
    private String maxDeliveryTime;

    @Column(name = "deliveryAreaInfo", length = 255)
    private String deliveryAreaInfo;

    // 배달 지역 설정 API, URL: /api/store/delivery-area
    @Column(name = "deliveryRange", length = 50)
    private String deliveryRange;

    // 주문 정보 조회 API, URL: /api/store/order-info & URL: /api/store/order-info
    @Column(name = "isTakeout")
    private Boolean isTakeout;

    @Column(name = "takeoutDiscount")
    private Long takeoutDiscount;

    @Column(name = "minOrderAmount")
    private Long minOrderAmount;

    @Column(name = "deliveryTip")
    private Long deliveryTip;
}