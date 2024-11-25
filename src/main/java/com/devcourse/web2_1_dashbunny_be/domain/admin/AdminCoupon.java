package com.devcourse.web2_1_dashbunny_be.domain.admin;

import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.DiscountType;
import jakarta.persistence.*;
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
@Table(name = "admin_coupon")
public class AdminCoupon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long couponId; //쿠폰 아이디

  @Column(nullable = false)
  private String couponName; //쿠폰명

  @Column(nullable = false)
  private Long discountPrice; //할인 금액

  @Column(nullable = false)
  private Long minOrderPrice; //최소 주문 금액

  @Column(nullable = false)
  private LocalDateTime expiredDate; //쿠폰 만료기한

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CouponStatus couponStatus = CouponStatus.PENDING; //쿠폰 상태 (대기, 활성화, 만료, 조기종료)

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CouponType couponType = CouponType.Regula; //쿠폰 유형 (일반, 선착순)

  @Column(nullable = false)
  private DiscountType discountType; //할인 타입 (정률,정액)

  @Column(nullable = true)
  private Long maximumDiscount; // 최대 할인 금액 (정률 방식에만 적용)

  private Long maxIssuance; //발급한도

  private LocalDateTime downloadStartDate; // (선착순 쿠폰일 경우)다운로드 시작 시간

  @Column(columnDefinition="TEXT", nullable = true)
  private String couponDescription; //쿠폰 상세 내용



}

// 쿠폰 아이디가 왜 uuid?