package com.devcourse.web2_1_dashbunny_be.domain.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

/**
 * 사용자 쿠폰 엔티티.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCoupon {

  @Id
  @UuidGenerator
  private String userCouponId;



}
