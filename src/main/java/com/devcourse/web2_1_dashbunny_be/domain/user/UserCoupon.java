package com.devcourse.web2_1_dashbunny_be.domain.user;

import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 쿠폰 엔티티.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_coupon")
public class UserCoupon {

  @Id
  @UuidGenerator
  private String userCouponId; //사용자 쿠폰 아이디

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user; // 사용자 엔티티와 연관관계

  @Column(nullable = false)
  private Long couponId; //쿠폰 아이디

//  @Column(nullable = false)
//  private String couponName; //쿠폰명

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private IssuedCouponType issuedCouponType; //관리자 쿠폰인지, 사장님 쿠폰인지

//  @Column(nullable = false)
//  private Long discountPrice; // 할인 금액
//
//  @Column(nullable = false)
//  private Long minOrderPrice; //최소 주문 금액
//
//  @Column(nullable = false)
//  private LocalDateTime issuedDate; //쿠폰 발급일자
//
//  @Column(nullable = false)
//  private LocalDateTime expiredDate; //쿠폰 만료 일자


  private LocalDateTime usedDate; //쿠폰 사용일자

  @Column(nullable = false)
  private boolean couponUsed = false; //쿠폰 사용 여부

//  private String couponDescription; //쿠폰 상세내용



}

//User엔티티에
//
//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//private List<UserCoupon> userCoupons = new ArrayList<>(); // 사용자가 소유한 쿠폰
//달아야함