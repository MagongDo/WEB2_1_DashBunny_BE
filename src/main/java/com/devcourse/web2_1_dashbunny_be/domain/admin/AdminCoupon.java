package com.devcourse.web2_1_dashbunny_be.domain.admin;

import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;


/**
 * 관리자 쿠폰 엔티티.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCoupon {

  @Id
  @UuidGenerator
  private String couponId; //쿠폰 아이디

  @Column(nullable = false)
  private String couponName; //쿠폰명

  @Column(nullable = false)
  private Long discountPrice; //할인 금액

  @Column(nullable = false)
  private Long minOrderPrice; //최소 주문 금액

  @Column(nullable = false)
  private LocalDateTime expiredDate; //쿠폰 만료기한

  @Column(nullable = false)
  private CouponStatus couponStatus = CouponStatus.PENDING; //쿠폰 상태

  @Column(nullable = false)
  private CouponType couponType = CouponType.Regula; //쿠폰 유형

  private Long maxIssuance; //발급한도

  private String couponDescription; //쿠폰 상세 내용



}

// 쿠폰 아이디가 왜 uuid?