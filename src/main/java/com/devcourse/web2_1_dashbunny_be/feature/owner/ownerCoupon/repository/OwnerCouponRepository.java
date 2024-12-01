package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerCouponRepository extends JpaRepository<OwnerCoupon, Long> {

    List<OwnerCoupon> findByStoreManagement_StoreIdAndCouponStatus(String storeId, CouponStatus couponStatus);
}
