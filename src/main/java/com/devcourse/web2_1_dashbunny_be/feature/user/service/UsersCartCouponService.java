package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository.AdminCouponRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository.OwnerCouponRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UserCartCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCheckCouponDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SocialUserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersCartRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * 장바구니에서 사용하는 쿠폰 서비스.
 */
@Service
@RequiredArgsConstructor
public class UsersCartCouponService {
  private final UserCouponRepository userCouponRepository;
  private final AdminCouponRepository adminCouponRepository;
  private final OwnerCouponRepository ownerCouponRepository;
  private final UserRepository userRepository;
  private final SocialUserRepository socialUserRepository;
  private final UsersCartRepository cartRepository;
  private final StoreManagementRepository storeManagementRepository;
  private final RedisTemplate<String, Object> redisTemplate;


  /**
   * 장바구니에서 사용 가능한 사용자 쿠폰 조회.
   * @param userId 사용자 id.
   * @return
   */
  @Transactional
  public List<UserCartCouponListResponseDto> findCouponsInCart(Long userId) {
    // 사용자와 장바구니 조회
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    Cart cart = cartRepository.findByUser(user);

    if (cart == null || cart.getCartItems().isEmpty()) {
      throw new IllegalArgumentException("장바구니가 비어 있습니다.");
    }

    // 장바구니 총 금액 계산
    Long totalPrice = cart.getCartItems().stream()
            .mapToLong(item -> item.getQuantity() * item.getMenuManagement().getPrice())
            .sum();

    // 장바구니에서 첫 번째 메뉴의 가게 ID 가져오기
    String storeId = cart.getCartItems().get(0).getMenuManagement().getStoreId();
    //가게 ID에 해당하는 가게 이름 가져오기
    String storeName = storeManagementRepository.findByStoreId(storeId).getStoreName();

    // 사용자 쿠폰 목록 가져오기 (만료되지 않은 쿠폰)
    List<UserCoupon> userCoupons = userCouponRepository.findByUserIdAndCouponUsedIsFalseAndIsExpiredIsFalse(userId);

    // 가게 ID와 관련된 쿠폰 우선 정렬, 그 외 쿠폰은 할인 금액 내림차순 정렬 (가게 ID가 다르면 X)
    return userCoupons.stream()
         .filter(coupon -> {
           if (coupon.getIssuedCouponType() == IssuedCouponType.OWNER) {
             // 사장님 쿠폰: storeId와 매칭 및 최소 주문 금액 조건
             OwnerCoupon ownerCoupon = ownerCouponRepository.findById(coupon.getCouponId())
                     .orElseThrow(() -> new IllegalArgumentException("사장님 쿠폰 정보를 찾을 수 없습니다."));

             return ownerCoupon.getStoreManagement().getStoreId().equals(storeId) &&
                     totalPrice >= ownerCoupon.getMinOrderPrice(); //장바구니 총금액이 쿠폰의 최소주문금액보다 커야함

           } else if (coupon.getIssuedCouponType() == IssuedCouponType.ADMIN) {
             // 관리자 쿠폰: 최소 주문 금액 조건
             AdminCoupon adminCoupon = adminCouponRepository.findById(coupon.getCouponId())
                     .orElseThrow(() -> new IllegalArgumentException("관리자 쿠폰 정보를 찾을 수 없습니다."));

             return totalPrice >= adminCoupon.getMinOrderPrice(); //장바구니 총금액이 쿠폰의 최소주문금액보다 커야함
           }
           return false;
         })
         .map(coupon -> {
           if (coupon.getIssuedCouponType() == IssuedCouponType.OWNER) {
             OwnerCoupon ownerCoupon = ownerCouponRepository.findById(coupon.getCouponId())
                     .orElseThrow(() -> new IllegalArgumentException("사장님 쿠폰 정보를 찾을 수 없습니다."));
             return new UserCartCouponListResponseDto(
                     coupon.getUserCouponId(),
                     ownerCoupon.getCouponName(),
                     ownerCoupon.getDiscountPrice(),
                     ownerCoupon.getDiscountType(),
                     ownerCoupon.getMinOrderPrice(),
                     ownerCoupon.getMaximumDiscount(),
                     ownerCoupon.getExpiredDate(),
                     ownerCoupon.getStoreManagement().getStoreName()
             );
           } else {
             AdminCoupon adminCoupon = adminCouponRepository.findById(coupon.getCouponId())
                     .orElseThrow(() -> new IllegalArgumentException("관리자 쿠폰 정보를 찾을 수 없습니다."));
             return new UserCartCouponListResponseDto(
                     coupon.getUserCouponId(),
                     adminCoupon.getCouponName(),
                     adminCoupon.getDiscountPrice(),
                     adminCoupon.getDiscountType(),
                     adminCoupon.getMinOrderPrice(),
                     adminCoupon.getMaximumDiscount(),
                     adminCoupon.getExpiredDate(),
                     null
             );
           }
         })
         .sorted(Comparator.comparing((UserCartCouponListResponseDto dto) ->
                         dto.getStoreName() != null && dto.getStoreName().equals(storeName)) // 가게 쿠폰 여부
                 .reversed() // 가게 쿠폰 우선 정렬
                 .thenComparing(UserCartCouponListResponseDto::getDiscountPrice, Comparator.reverseOrder())) // 할인 금액 내림차순
         .toList();
  }

  /**
   장바구니에서 하나의 쿠폰을 선택하는 메소드.
   */
  @Transactional
  public UsersCheckCouponDto selectCouponById(Long userId, String userCouponId) {
    UserCoupon selectedCoupon = userCouponRepository.findByUserIdAndUserCouponId(userId, userCouponId);

    //사장님이 발급한 쿠폰이라면
    if(selectedCoupon.getIssuedCouponType().equals(IssuedCouponType.OWNER)) {
      OwnerCoupon ownerCoupon = ownerCouponRepository.findById(selectedCoupon.getCouponId())
              .orElseThrow(() -> new IllegalArgumentException("사장님 쿠폰 정보를 찾을 수 없습니다."));

      return new UsersCheckCouponDto(
              userCouponId,
              ownerCoupon.getDiscountPrice(),
              ownerCoupon.getDiscountType(),
              ownerCoupon.getMinOrderPrice(),
              ownerCoupon.getMaximumDiscount()
      );
    }
    //관리자가 발급한 쿠폰(일반 쿠폰, 선착순쿠폰)이라면
    else if(selectedCoupon.getIssuedCouponType().equals(IssuedCouponType.ADMIN)) {
      AdminCoupon adminCoupon = adminCouponRepository.findById(selectedCoupon.getCouponId())
              .orElseThrow(() -> new IllegalArgumentException("사장님 쿠폰 정보를 찾을 수 없습니다."));

      return new UsersCheckCouponDto(
              userCouponId,
              adminCoupon.getDiscountPrice(),
              adminCoupon.getDiscountType(),
              adminCoupon.getMinOrderPrice(),
              adminCoupon.getMaximumDiscount()
      );
    }
    return null;
  }
}
