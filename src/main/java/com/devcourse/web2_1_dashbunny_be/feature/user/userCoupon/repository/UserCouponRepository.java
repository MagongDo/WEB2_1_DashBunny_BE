package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, String> {


  /**
   * 사용자가 같은 유형의 쿠폰을 발급했는지 확인.
   */
  boolean existsByUser_UserIdAndCouponIdAndIssuedCouponType(Long userId, Long couponId, IssuedCouponType issuedCouponType);


  /**
   * 사용자의 쿠폰이 만료기한이 지났는지 확인하기 위한 쿠폰 목록 조회.
   */
  List<UserCoupon> findByCouponIdAndAndIssuedCouponType(Long couponId, IssuedCouponType issuedCouponType);

  /**
   * 사용하지 않은 쿠폰 목록 조회.
   */
  List<UserCoupon> findByUser_UserIdAndCouponUsedIsFalseAndExpiredIsFalse(Long userId);


  /**
   * 이미 사용자가 다운로드 받은 일반 쿠폰 id 조회.
   */
  @Query("SELECT uc.couponId FROM UserCoupon uc WHERE uc.user.userId = :userId AND uc.issuedCouponType = :issuedCouponType ")
  List<Long> findCouponIdsByUser_UserIdAndIssuedCouponType(@Param("userId") Long userId, @Param("issuedCouponType") IssuedCouponType issuedCouponType);

  /**
   * 사용자의 특정 쿠폰 조회.
   */
  UserCoupon findByUser_UserIdAndUserCouponId(Long userId, String userCouponId);

}
