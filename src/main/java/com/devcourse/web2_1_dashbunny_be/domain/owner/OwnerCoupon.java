package com.devcourse.web2_1_dashbunny_be.domain.owner;


import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.DiscountType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;
import org.hibernate.annotations.UuidGenerator;



/**
 * 사장님 쿠폰 정보를 관리하는 엔티티 클래스.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "owner_coupon")
public class OwnerCoupon {

  // 사장님 쿠폰 ID (자동 생성)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long couponId; //쿠폰 아이디

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long couponId;


  // 스토어 없이 쿠폰은 생성이 불가능하다.
  @ManyToOne
  @JoinColumn(name = "store_id", nullable = false)
  private StoreManagement storeManagement;


  // 쿠폰명 (필수, 최대 길이 255자)
  @Column(nullable = false, length = 255)
  private String couponName;

  // 할인 금액 (필수)
  @Column(nullable = false)
  private Long discountPrice;

  // 최소 주문 금액 (필수)
  @Column(nullable = false)
  private Long minOrderPrice;

  // 만료기한 (필수)
  @Column(nullable = false)
  private LocalDateTime expiredDate;

  // 쿠폰 승인 상태 (열거형, 필수)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private CouponStatus couponStatus; //쿠폰 상태 (대기, 활성화, 만료, 조기종료)

  // 할인 방식 (열거형, 필수)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private DiscountType discountType; //할인 타입 (정률,정액)

  // 최대 할인 금액 (할인 방식이 퍼센트일 경우 적용)
  private Long maximumDiscount;

  // 쿠폰 상세 내용 (긴 텍스트 저장)
  @Column(columnDefinition="TEXT", nullable = true)
  private String couponDescription;

}
//단일조회