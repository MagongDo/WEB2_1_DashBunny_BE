package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 가게의 배달 범위를 정하는 깃발 엔티티
@Entity
@Table(name = "flag")
@Getter
@Setter
public class StoreFlag {

    // 깃발 ID (자동 생성)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flag_id", nullable = false)  // 명시적으로 매핑
    private Long flagId;

    // 가게 관리 엔티티와 연관 관계 설정
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreManagement storeManagement;

    private double latitude;

    private double longitude;
}