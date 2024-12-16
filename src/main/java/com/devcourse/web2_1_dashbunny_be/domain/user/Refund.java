package com.devcourse.web2_1_dashbunny_be.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Refund {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long payment;
    private String refundId;      // Toss Payments에서 제공하는 refundId
    private Long amount;          // 환불 금액 (원 단위)
    private String reason;        // 환불 사유
    private String status;        // REQUESTED, COMPLETED, FAILED 등
    private LocalDateTime refundedAt;
}
