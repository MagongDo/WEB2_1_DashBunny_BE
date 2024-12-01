package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, String> {

//    UserCoupon findByCouponIdAndCouponType(Long couponId, String couponType);

  /**
   * 사용자가 같은 유형의 쿠폰을 발급했는지 확인.
   */
  boolean existsByUser_UserIdAndCouponIdAndIssuedCouponType(Long userId, Long couponId, IssuedCouponType issuedCouponType);

  /**
   * 특정 사용자가 특정 쿠폰을 이미 발급받았는지 확인.
   */
  //boolean existsByUser_IdAndCouponId(Long userId, Long couponId);

  /**
   * 사용하지 않은 쿠폰 목록 조회.
   */
  List<UserCoupon> findByUser_UserIdAndCouponUsedIsFalse(Long userId);

  /**
   * 이미 사용자가 다운로드 받은 일반 쿠폰 id 조회.
   */
  @Query("SELECT uc.couponId FROM UserCoupon uc WHERE uc.user.userId = :userId AND uc.issuedCouponType = :issuedCouponType ")
  List<Long> findCouponIdsByUser_UserIdAndIssuedCouponType(@Param("userId") Long userId, @Param("issuedCouponType") IssuedCouponType issuedCouponType);


}
