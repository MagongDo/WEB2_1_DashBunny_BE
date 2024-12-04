/*
package com.devcourse.web2_1_dashbunny_be.domain.admin;

import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository.AdminCouponRepository;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AdminCouponTest {

  @Autowired
  private AdminCouponRepository adminCouponRepository;

  @Test
  void saveCouponRepository() {
    //Given
    AdminCoupon coupon = AdminCoupon.builder()
            .couponId("Test123")
            .couponName("welcome!")
            .discountPrice(5000L)
            .minOrderPrice(15000L)
            .expiredDate(LocalDateTime.now().plusDays(30))
            .couponType(CouponType.Regula)
            .couponDescription("A welcome discount coupon")
            .build();

    //When
    AdminCoupon savedCoupon = adminCouponRepository.save(coupon);
    AdminCoupon foundCoupon = adminCouponRepository.findById(savedCoupon.getCouponId()).orElse(null);

    System.out.println("Saved Coupon: " + savedCoupon);
    System.out.println("Found Coupon: " + foundCoupon);

    //Then
    assertThat(foundCoupon).isNotNull();
    assertThat(foundCoupon.getCouponName()).isEqualTo("welcome!");
    assertThat(foundCoupon.getDiscountPrice()).isEqualTo(5000L);
  }
}*/
