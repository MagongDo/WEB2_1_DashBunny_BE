package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, String> {

    UserCoupon findByCouponIdAndCouponType(Long couponId, String couponType);

    /**
     * 사용자가 같은 쿠폰을 발급했는지 확인.
     */
    boolean existsByUser_IdAndCouponIdAndIssuedCouponType(Long userId, Long couponId, IssuedCouponType issuedCouponType);
}
