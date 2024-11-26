package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerCouponRepository extends JpaRepository<OwnerCoupon, Long> {
}
