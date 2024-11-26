package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 관리자 쿠폰 레포지토리.
 */
@Repository
public interface AdminCouponRepository extends JpaRepository<AdminCoupon, Long> {

  /**
   * 기한이 지난 쿠폰 중 아직 EXPIRED 상태가 아닌 쿠폰 조회 (현재 시간 기준).
   */
  List<AdminCoupon> findByExpiredDateBeforeAndCouponStatusNot(LocalDateTime now, CouponStatus status);

  /**
   * 특정 쿠폰 상태를 제외한 목록 반환.
   */
  List<AdminCoupon> findByCouponStatusNot(CouponStatus status);

  /**
   * 관리자가 발급한 활성화된 일반 쿠폰 조회.
   */
  List<AdminCoupon> findByCouponTypeAndCouponStatus(CouponType couponType, CouponStatus couponStatus);

}
