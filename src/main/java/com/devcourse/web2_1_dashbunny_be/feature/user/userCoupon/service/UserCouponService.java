package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import com.devcourse.web2_1_dashbunny_be.exception.CustomException;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository.AdminCouponRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository.OwnerCouponRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.Util.SecurityUtil;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SocialUserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.FirstComeCouponResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.FirstComeCouponResponseWrapper;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.GeneralCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.UserCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 사용자 쿠폰 서비스.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class UserCouponService {
  private final UserCouponRepository userCouponRepository;
  private final AdminCouponRepository adminCouponRepository;
  private final OwnerCouponRepository ownerCouponRepository;
  private final UserRepository userRepository;
  private final SocialUserRepository socialUserRepository;


  /**
   * 관리자가 발급한 활성화된 일반 쿠폰 목록을 조회하는 메서드.
   */
  public List<GeneralCouponListResponseDto> findActiveRegularCoupons() {
    List<AdminCoupon> adminCoupons = adminCouponRepository.findByCouponTypeAndCouponStatus(
            CouponType.Regula,
            CouponStatus.ACTIVE);
    return adminCoupons.stream()
            .map(GeneralCouponListResponseDto::new)
            .toList();
  }

  /**
   * 관리자가 발급한 활성화된 선착순 쿠폰을 조회하는 메서드.
   */
  public FirstComeCouponResponseWrapper findActiveFirstComeCoupon() {

    //활성화된 선착순 쿠폰이 있는니 확인
    AdminCoupon activeFirstComeCoupon = adminCouponRepository.findFirstByCouponTypeAndCouponStatus(
            CouponType.FirstCome,
            CouponStatus.ACTIVE);

    if (activeFirstComeCoupon == null) {
      return FirstComeCouponResponseWrapper.empty(); // 활성화된 선착순 쿠폰이 없음
    }

    //현재 사용자 정보
    User currentUser = currentUserValidation();

    //이미 다운로드를 반았는지 확인
    boolean hasParticipated = userCouponRepository.existsByUser_UserIdAndCouponIdAndIssuedCouponType(
            currentUser.getUserId(),
            activeFirstComeCoupon.getCouponId(),
            IssuedCouponType.ADMIN
    );

    if (hasParticipated) {
      // 이미 참여한 사용자
      return FirstComeCouponResponseWrapper.alreadyParticipated("이미 선착순 쿠폰 이벤트에 참여하셨습니다.");
    } else {
      // 참여하지 않은 사용자, 쿠폰 정보 제공
      FirstComeCouponResponseDto dto = new FirstComeCouponResponseDto(activeFirstComeCoupon);
      return FirstComeCouponResponseWrapper.success(dto);
    }

  }


  /**
   * 관리자가 발급한 활성화된  쿠폰을 다운로드하는 메서드.
   */
  @Transactional
  public UserCoupon downloadCoupon(Long couponId, IssuedCouponType issuedCouponType) {

    // 현재 로그인된 사용자 정보 가져오기
    User currentUser = currentUserValidation();

    //이미 다운로드한 쿠폰인지 확인
    boolean alreadyDownloaded = userCouponRepository.existsByUser_UserIdAndCouponIdAndIssuedCouponType(currentUser.getUserId(), couponId, issuedCouponType);

    // 다운로드 받은 적이 있으면 에러 메시지 반환
    if (alreadyDownloaded) {
      throw new CustomException("중복 다운로드가 불가능한 쿠폰입니다.", HttpStatus.BAD_REQUEST);
    }

    /**
     * 발급 유형별 다운로드 로직 처리.
     */
    switch (issuedCouponType) {
      case ADMIN: //관리자가 발급한 쿠폰
        return downloadAdminCoupon(currentUser, couponId);
      case OWNER: //사장님이 발급한 쿠폰
        return downloadOwnerCoupon(currentUser, couponId);
      default:
        throw new CustomException("잘못된 쿠폰 유형입니다.", HttpStatus.BAD_REQUEST);
    }
  }


  /**
   * 관리자가 발급한 쿠폰 다운로드 메소드.
   */
  private UserCoupon downloadAdminCoupon(User currentUser, Long couponId) {
    //Admin 쿠폰 조회
    AdminCoupon adminCoupon = adminCouponRepository.findById(couponId)
            .orElseThrow(() -> new CustomException("해당 쿠폰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

    //선착순 쿠폰이면 분리된 메소드 이용(레디스 사용)
    if (adminCoupon.getCouponType() == CouponType.FirstCome) {
      return downloadFirstComeCoupon(currentUser, couponId);
    }

    //UserCoupon 객체 생성
    UserCoupon downloadCoupon = UserCoupon.builder()
            .user(currentUser)
            .couponId(couponId)
            .issuedCouponType(IssuedCouponType.ADMIN)
            .usedDate(null)
            .couponUsed(false)
            .build();

    return userCouponRepository.save(downloadCoupon); //쿠폰 저장
  }

  /**
   * 사장님이 발급한 쿠폰 다운로드 메소드.
   * @param currentUser
   * @param couponId
   * @return
   */
  private UserCoupon downloadOwnerCoupon(User currentUser, Long couponId) {
    //Owner 쿠폰 조회
    OwnerCoupon ownerCoupon = ownerCouponRepository.findById(couponId)
            .orElseThrow(() -> new CustomException("해당 쿠폰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
    //UserCoupon 객체 생성
    UserCoupon downloadCoupon = UserCoupon.builder()
            .user(currentUser)
            .couponId(couponId)
            .issuedCouponType(IssuedCouponType.OWNER)
            .usedDate(null)
            .couponUsed(false)
            .build();

    return userCouponRepository.save(downloadCoupon); //쿠폰 저장

  }

  /**
   * 관리자가 발급한 선착순 쿠폰 다운로드 메서드.
   */
  private UserCoupon downloadFirstComeCoupon(User currentUser, Long couponId) {
    return null;
      //레디스 사용
  }

  /**
   * 현재 사용자 쿠폰함 목록을 조회하는 메소드.
   */
  public List<UserCouponListResponseDto> findNotUsedCoupons(){
    User currentUser = currentUserValidation();
    List<UserCoupon> availableCoupons = userCouponRepository.findByUser_UserIdAndCouponUsedIsFalse(currentUser.getUserId());

    // 발급 유형에 따라 AdminCoupon 또는 OwnerCoupon 정보를 매핑
    return availableCoupons.stream().map(coupon -> {
      if (coupon.getIssuedCouponType() == IssuedCouponType.ADMIN) {
        AdminCoupon adminCoupon = adminCouponRepository.findById(coupon.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("관리자 쿠폰 정보를 찾을 수 없습니다."));
        return new UserCouponListResponseDto(
                coupon.getUserCouponId(),
                adminCoupon.getCouponName(),
                adminCoupon.getDiscountPrice(),
                adminCoupon.getDiscountType(),
                adminCoupon.getMinOrderPrice(),
                adminCoupon.getMaximumDiscount(),
                adminCoupon.getExpiredDate(),
                adminCoupon.getCouponDescription()
        );
      } else if (coupon.getIssuedCouponType() == IssuedCouponType.OWNER) {
        OwnerCoupon ownerCoupon = ownerCouponRepository.findById(coupon.getCouponId())
                .orElseThrow(() -> new IllegalArgumentException("가게 쿠폰 정보를 찾을 수 없습니다."));
        return new UserCouponListResponseDto(
                coupon.getUserCouponId(),
                ownerCoupon.getCouponName(),
                ownerCoupon.getDiscountPrice(),
                ownerCoupon.getDiscountType(),
                ownerCoupon.getMinOrderPrice(),
                ownerCoupon.getMaximumDiscount(),
                ownerCoupon.getExpiredDate(),
                ownerCoupon.getCouponDescription()
        );
      } else {
        throw new CustomException("잘못된 쿠폰 유형입니다.", HttpStatus.BAD_REQUEST);
      }
    })// 정렬: discountType 기준 내림차순
            .sorted(Comparator.comparing(UserCouponListResponseDto::getDiscountType).reversed())
            .toList();


  }



  /**
   * 현재 사용자의 로그인 정보를 조회.
   */
  public User currentUserValidation() {
    //String currentUser = SecurityUtil.getCurrentUsername(); // 현재 로그인한 사용자 ID (전화번호 또는 providerId)
    String currentUser = SecurityContextHolder.getContext().getAuthentication().getName(); // 현재 로그인한 사용자 ID (전화번호 또는 providerId)

    Optional<User> generalUser = userRepository.findByPhone(currentUser);  //일반 로그인 사용자인지 확인

    if (generalUser.isPresent()) {
      return generalUser.get();
    }


    Optional<SocialUser> socialUser = socialUserRepository.findByProviderId(currentUser); //소셜 로그인 사용자인지 확인

    if (socialUser.isPresent()) {
      return userRepository.findById(socialUser.get().getUserId())
                .orElseThrow(() -> new IllegalStateException("해당 소셜 사용자에 연결된 일반 사용자를 찾을 수 없습니다."));
    }

    // 예외 처리
    throw new IllegalStateException("로그인된 사용자를 찾을 수 없습니다.");
  }

}
