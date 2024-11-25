package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class AddAdminCouponRequestDtoTest {

  @Test
  void testToEntity() {
    //GIVEN
    AddAdminCouponRequestDto dto = new AddAdminCouponRequestDto(
            "Welcome Coupon",
            CouponType.Regula,
            CouponStatus.ACTIVE,
            5000L,
            15000L,
            100L,
            LocalDateTime.of(2024, 12, 31, 23, 59),
            "This is a welcome coupon"
    );

    //WHEN
    AdminCoupon entity = dto.toEntity();

    //THEN
    assertThat(entity.getCouponName()).isEqualTo("Welcome Coupon");
    assertThat(entity.getCouponType()).isEqualTo(CouponType.Regula);
    assertThat(entity.getCouponStatus()).isEqualTo(CouponStatus.ACTIVE);
    assertThat(entity.getDiscountPrice()).isEqualTo(5000L);
    assertThat(entity.getMinOrderPrice()).isEqualTo(15000L);
    assertThat(entity.getMaxIssuance()).isEqualTo(100L);
    assertThat(entity.getExpiredDate()).isEqualTo(LocalDateTime.of(2024, 12, 31, 23, 59));
    assertThat(entity.getCouponDescription()).isEqualTo("This is a welcome coupon");

  }

}