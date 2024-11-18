package com.devcourse.web2_1_dashbunny_be.domain.owner;

import com.devcourse.dashbunny.domain.owner.role.CouponStatus;
import com.devcourse.dashbunny.domain.owner.role.DiscountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

// 사장님 쿠폰 정보를 관리하는 엔티티 클래스
@Getter
@Setter
@Entity
@Table(name = "owner_coupon")
public class OwnerCoupon {

    // 사장님 쿠폰 ID (자동 생성)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    //단순 id 참조에서 객체 중심으로 변경해 보았는데 어떤 방식이 더 좋으신가요?
    // 강민 : 와 이걸 이제 이해했습니다,,객체 중심이 더 좋은거 같습니다!!
  /*  // 가게 ID (필수)
    @Column(nullable = true)
    private String storeId;*/


    // 스토어 없이 쿠폰은 생성이 불가능하다.
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreManagement storeManagement;

    // 쿠폰 승인 상태 (열거형, 필수)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CouponStatus couponStatus;

    // 쿠폰명 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String couponName;

    // 할인 금액 (필수)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountPrice;

    // 최소 주문 금액 (필수)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minDeliveryPrice;

    // 만료기한 (필수)
    @Column(nullable = false)
    private LocalDate expiryDate;

    // 할인 방식 (열거형, 필수)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DiscountType discountType;

    // 최대 할인 금액 (할인 방식이 퍼센트일 경우 적용)
    @Column(precision = 10, scale = 2)
    private BigInteger maximumDiscount;

}
