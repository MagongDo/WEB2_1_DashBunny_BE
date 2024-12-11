package com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.ownerCoupon.OwnerCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository.OwnerCouponRepository;
import java.time.LocalDateTime;
import java.util.List;

import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 쿠폰 서비스 로직 구현 class.
 */
@Service
@RequiredArgsConstructor
public class OwnerCouponServiceImpl implements OwnerCouponService {

  private final Validator validator;
  private final OwnerCouponRepository ownerCouponRepository;
  private final UserCouponRepository userCouponRepository;

  @Override
  public List<OwnerCouponListResponseDto> getCounponList(String storeId) {
    StoreManagement store = validator.validateStoreId(storeId);
    List<OwnerCouponListResponseDto> response = store.getCouponList().stream()
           .map(OwnerCouponListResponseDto::fromEntity).toList();
    return response;
  }

  @Override
  public void updateCouponStatus(Long couponId) {
    OwnerCoupon coupon = validator.validateCouponId(couponId);
    coupon.setCouponStatus(CouponStatus.EARLY_TERMINATED);
    ownerCouponRepository.save(coupon);
  }

  //-1 시키키
  //반환 할때도 테스트 해보기
  @Override
  public void saveOwnerCoupon(OwnerCoupon ownerCoupon, int day) {
    LocalDateTime time = ownerCoupon.getExpiredDate();
    LocalDateTime expireDate = time.plusDays(day);
    ownerCoupon.setExpiredDate(expireDate);
    ownerCouponRepository.save(ownerCoupon);
  }
  //검증이 지금 가게에 대한 부분을 컨트롤러에서 하고 있다. 이걸 서비스단으로 옮기는 작업을 해야한다.

  /**
   * 매일 자정마다 쿠폰의 만료기한 확인후 만료기한이 지난 쿠폰은 쿠폰 상태를 EXPIRED로 변환.
   */
  @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
  public void updateExpiredCoupons() {
    List<OwnerCoupon> expiredCoupons = ownerCouponRepository.findByExpiredDateBeforeAndCouponStatusNot(
            LocalDateTime.now(), CouponStatus.EXPIRED);

    for (OwnerCoupon coupon : expiredCoupons) {
      coupon.setCouponStatus(CouponStatus.EXPIRED);

      //사용자 쿠폰 만료상태 변환
      List<UserCoupon> expiredUserCoupons = userCouponRepository.findByCouponIdAndAndIssuedCouponType(coupon.getCouponId(), IssuedCouponType.OWNER);
      for(UserCoupon userCoupon : expiredUserCoupons) {
        userCoupon.setExpired(true);
      }
      userCouponRepository.saveAll(expiredUserCoupons);
    }

   ownerCouponRepository.saveAll(expiredCoupons);

  }
}
