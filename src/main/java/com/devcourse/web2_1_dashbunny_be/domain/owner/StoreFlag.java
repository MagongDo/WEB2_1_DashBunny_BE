package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 가게의 배달 범위를 정하는 깃발 엔티티
@Entity
@Table(name = "flag")
public class StoreFlag {

    // 깃발 ID (자동 생성)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flagId;

    // 가게 ID (필수)
    @Column(nullable = false)
    private Long storeId;

    // 위도 (소수점 6자리까지)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal latitude;

    // 경도 (소수점 6자리까지)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal longitude;



}