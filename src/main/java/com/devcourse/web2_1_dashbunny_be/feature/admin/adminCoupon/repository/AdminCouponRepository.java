package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 관리자 쿠폰 레포지토리.
 */
public interface AdminCouponRepository extends JpaRepository<AdminCoupon, Long> {

}
