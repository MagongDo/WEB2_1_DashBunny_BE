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

    // 깃발 레벨 ENUM (레벨에 따라, 배달 범위 증가, 원을 기준으로, lv1 = 1km)
    @Column(nullable = false)
    private Long flagLevel;

    // 위치 정확도 (소수점 6자리까지)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal accuracy;

    // 고도 (소수점 6자리까지)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal altitude;

    // 고도 정확도 (소수점 6자리까지)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal altitudeAccuracy;

    // 방향 (소수점 6자리까지)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal heading;

    // 위도 (소수점 6자리까지)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal latitude;

    // 경도 (소수점 6자리까지)
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal longitude;

    // 데이터 기록 시간 (필수)
    @Column(nullable = false)
    private LocalDateTime recordedTime;

}