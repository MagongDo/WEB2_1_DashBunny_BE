package com.devcourse.web2_1_dashbunny_be.domain.user;

import com.devcourse.web2_1_dashbunny_be.domain.user.role.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderId;
  private String orderName;
  private Long amount;
  private String customerName;
  private String paymentKey;   // 결제 완료 시 발급받은 키
  private String status;       // READY, DONE, FAIL 등
  private String failReason;   // 실패 시 사유
  private String method;
  private LocalDateTime updatedAt;

}
