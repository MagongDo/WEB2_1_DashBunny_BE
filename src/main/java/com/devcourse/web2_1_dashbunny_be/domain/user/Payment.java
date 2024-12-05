package com.devcourse.web2_1_dashbunny_be.domain.user;

import com.devcourse.web2_1_dashbunny_be.domain.user.role.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // 내부 결제 ID

  @Column(unique = true)
  private String paymentId; // 토스 결제 고유 ID

  @Column(nullable = false)
  private Long cartId; // 장바구니와의 관계

  @Column(nullable = false)
  private Long amount; // 결제 금액 (원 단위)

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status; // 결제 상태

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime createdAt; //생성 날짜

  @LastModifiedDate
  private LocalDateTime updatedAt; //수정 날짜
}