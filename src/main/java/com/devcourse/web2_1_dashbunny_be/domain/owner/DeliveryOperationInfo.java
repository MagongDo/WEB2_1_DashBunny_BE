/*
package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DeliveryOperationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long deliveryInfoId; // 배달 운영 정보 ID

    @OneToOne
    @JoinColumn(name = "store_id", nullable = false, unique = true)
    private StoreManagement storeManagement; // StoreManagement와 1:1 관계

    @Lob
    @Column(nullable = true,  length = 255)
    private String deliveryAreaInfo; // 배달 지역 안내 (긴 텍스트 가능)

    @Column(nullable = true,  length = 50)
    private String minDeliveryTime; // 최소 배달 예상 시간 (단위: 분)

    @Column(nullable = true, length = 50)
    private String maxDeliveryTime; // 최대 배달 예상 시간 (단위: 분)

    @Column(nullable = true, length = 255)
    private Long baseDeliveryTip; // 기본 배달 팁 (단위: 금액)

    @Column(nullable = true, length = 255)
    private Long minOrderAmount; // 최소 주문 금액 (단위: 금액)

}*/
