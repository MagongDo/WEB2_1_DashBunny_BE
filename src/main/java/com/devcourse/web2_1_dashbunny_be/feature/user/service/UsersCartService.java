package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.config.TossPaymentConfig;
import com.devcourse.web2_1_dashbunny_be.domain.common.role.DiscountType;
import com.devcourse.web2_1_dashbunny_be.domain.owner.DeliveryOperatingInfo;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.*;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.DeliveryOperatingInfoRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCartItemDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCartResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.cart.UsersCheckCouponDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.PaymentRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersCartRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * 사용자의 장바구니 수정, 조회, 추가, 삭제, 결제를 포함한 모든 기능을 제공하는 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UsersCartService {

  private final UsersCartRepository cartRepository; // 장바구니 저장 및 조회
  private final MenuRepository menuManagementRepository; // 메뉴 정보 저장소
  private final StoreManagementRepository storeManagementRepository; // 가게 정보 저장소
  private final DeliveryOperatingInfoRepository deliveryOperationInfoRepository; // 배달 정보 저장소
  private final UserRepository userRepository; // 사용자 정보 저장소
  private final PaymentService paymentService; // 결제 서비스
  private final UserCouponRepository userCouponRepository; //사용자 쿠폰 저장소
  private final TossPaymentConfig tossPaymentConfig;
  private final IdempotencyKeyService idempotencyKeyService;
  private final PaymentRepository paymentRepository;


  /**
   * 사용자의 장바구니를 생성하는 기능.
   * @param userId 사용자 ID.
   */
  public void createCart(String userId) {
    // 사용자를 조회, 없으면 예외 발생
    User user = userRepository.findByPhone(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    // 사용자의 장바구니가 없으면 새로 생성
    if (cartRepository.findByUser(user) == null) {
      Cart cart = Cart.builder().user(user).build(); // 빈 장바구니 생성
      cartRepository.save(cart); // 장바구니 저장
    }
  }

  /**
   * 사용자의 장바구니 정보를 조회하는 메소드.
   * @param userId 사용자 ID.
   * @return 장바구니 정보 DTO
   */
  public UsersCartResponseDto getCart(String userId) {
    // 사용자 조회 및 장바구니 확인
    User user = userRepository.findByPhone(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    Cart cart = cartRepository.findByUser(user);

    // 장바구니가 없으면 생성 후 조회
    if (cart == null) {
      createCart(userId);
      cart = cartRepository.findByUser(user);
    }

    // 가게 ID가 없으면 빈 장바구니로 간주
    if (cart.getStoreId() == null) {
      return null;
    } else {
      // 가게와 배달 정보를 가져와 DTO로 반환
      StoreManagement store = storeManagementRepository.findById(cart.getStoreId())
              .orElseThrow(IllegalArgumentException::new);

      DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperationInfoRepository.findByStoreId(store.getStoreId());

      return UsersCartResponseDto.toUsersCartDto(cart, store.getStoreName(), deliveryOperatingInfo.getDeliveryTip(), null);
    }
  }

  /**
   * 장바구니에 메뉴를 추가하거나 수량을 업데이트하는 메소드.
   * @param userId 사용자 ID
   * @param menuId 메뉴 ID
   * @param quantity 추가할 수량
   * @param overwrite 가게가 다를 때 기존 장바구니 초기화 여부
   * @return 업데이트된 장바구니 정보
   */
  @Transactional
  public UsersCartResponseDto addMenuToCart(String userId, Long menuId, Long quantity, Boolean overwrite) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
    }

    User user = userRepository.findByPhone(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    Cart cart = cartRepository.findByUser(user);

    if (cart == null) {
      cart = Cart.builder()
              .user(user)
              .cartItems(new ArrayList<>())
              .build();
      cartRepository.save(cart);
    }

    MenuManagement menu = menuManagementRepository.findById(menuId)
            .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
    StoreManagement store = storeManagementRepository.findById(menu.getStoreId())
            .orElseThrow(IllegalArgumentException::new);

    DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperationInfoRepository.findByStoreId(store.getStoreId());

    // 가게가 다른 경우 처리 로직
    if (cart.getStoreId() != null && !cart.getStoreId().equals(store.getStoreId())) {
      // overwrite가 아직 결정되지 않은(null) 상태면 예외 발생 → 클라이언트가 어떻게 할지 선택하도록 함
      if (overwrite == null) {
        throw new IllegalArgumentException("현재 장바구니에 다른 가게의 메뉴가 담겨 있습니다. overwrite 파라미터를 true 또는 false로 재요청해주세요.");
      } else if (!overwrite) {
        // 클라이언트가 overwrite=false로 재요청 → 기존 가게 유지, 새로운 메뉴 추가하지 않고 현재 상태 반환
        return UsersCartResponseDto.toUsersCartDto(cart,
                storeManagementRepository.findById(cart.getStoreId()).orElseThrow().getStoreName(),
                deliveryOperationInfoRepository.findByStoreId(cart.getStoreId()).getDeliveryTip(), null);
      } else {
        // overwrite=true 경우 → 기존 장바구니 초기화 후 새로운 가게로 교체
        cart.getCartItems().clear();
        cart.setStoreId(null);
      }
    }

    // 여기 도착했다면 같은 가게거나, overwrite=true로 장바구니 초기화 완료
    cart.setStoreId(store.getStoreId());

    Optional<CartItem> existingItem = cart.getCartItems().stream()
            .filter(item -> item.getMenuManagement().getMenuId().equals(menuId))
            .findFirst();

    if (existingItem.isPresent()) {
      CartItem cartItem = existingItem.get();
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    } else {
      CartItem newItem = CartItem.builder()
              .cart(cart)
              .menuManagement(menu)
              .quantity(quantity)
              .build();
      cart.getCartItems().add(newItem);
    }

    cart.setTotalPrice(calculateTotalPrice(cart));
    cartRepository.save(cart);

    return UsersCartResponseDto.toUsersCartDto(cart, store.getStoreName(), deliveryOperatingInfo.getDeliveryTip(), null);
  }


  /**
   * 장바구니에서 특정 메뉴의 수량을 업데이트하거나 삭제하는 메소드.
   * @param userId 사용자 ID
   * @param menuId 메뉴 ID
   * @param counts 변경할 수량 (양수: 추가, 음수: 감소)
   * @return 업데이트된 장바구니 정보
   */
  public UsersCartResponseDto updateItemQuantity(String userId, Long menuId, Long counts) {
    // 사용자, 장바구니, 메뉴, 가게 및 배달 정보를 조회
    User user = userRepository.findByPhone(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    Cart cart = cartRepository.findByUser(user);
    MenuManagement menu = menuManagementRepository.findById(menuId).orElseThrow(IllegalArgumentException::new);
    StoreManagement store = storeManagementRepository.findById(menu.getStoreId()).orElseThrow(IllegalArgumentException::new);
    DeliveryOperatingInfo deliveryOperatingInfo = deliveryOperationInfoRepository.findByStoreId(store.getStoreId());
    List<CartItem> itemsToRemove = new ArrayList<>();

    // 장바구니의 아이템을 순회하며 수량 업데이트
    cart.getCartItems().forEach(item -> {
      if (item.getMenuManagement().getMenuId().equals(menuId)) {
        long quantity = item.getQuantity() + counts;
        if (quantity <= 0) {
          itemsToRemove.add(item); // 수량이 0 이하인 아이템 삭제 목록에 추가
        } else {
          item.setQuantity(quantity); // 수량 업데이트
        }
      }
    });

    // 삭제 대상 아이템 제거
    cart.getCartItems().removeAll(itemsToRemove);
    if (cart.getCartItems().isEmpty()) {
      cart.setStoreId(null);
      cart.setTotalPrice(null);
      cartRepository.save(cart);
      return getCart(userId); // 빈 장바구니 반환
    } else {
      cart.setTotalPrice(calculateTotalPrice(cart));
      cartRepository.save(cart);
      return UsersCartResponseDto.toUsersCartDto(cart, store.getStoreName(), deliveryOperatingInfo.getDeliveryTip(), null);
    }
  }

  /**
   * 장바구니의 총 금액 계산 메소드.
   * @param cart 장바구니 엔티티
   * @return 총 금액
   */
  private Long calculateTotalPrice(Cart cart) {
    return cart.getCartItems().stream()
            .mapToLong(item -> item.getQuantity() * item.getMenuManagement().getPrice()) // 수량 * 가격
            .sum();
  }

  /**
   * 장바구니 결제 처리 메소드.
   * @param userId 사용자 ID
   * @param storeRequirement 가게 요청사항
   * @param deliveryRequirement 배달 요청사항
   * @return 결제 후 장바구니 정보
   */

  @Transactional
  public UsersCartResponseDto checkoutCart(String userId, String storeRequirement, String deliveryRequirement, String method) {
    // 장바구니 확인
    UsersCartResponseDto cartDto = getCart(userId);
    if (cartDto == null || cartDto.getCartItems() == null || cartDto.getCartItems().isEmpty()) {
      throw new IllegalArgumentException("Cart is empty");
    }

    // 장바구니 엔티티 조회
    Cart cart = cartRepository.findById(cartDto.getCartId())
            .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

    // 장바구니 아이템을 DTO로 변환
    List<UsersCartItemDto> cartItemDtos = cart.getCartItems().stream()
            .map(UsersCartItemDto::toUsersCartItemDto)
            .toList();


    // 총 금액 및 배달료 합산
    Long totalPrice = cartItemDtos.stream()
            .mapToLong(UsersCartItemDto::getTotalPrice)
            .sum();

    // 쿠폰 할인 금액 계산
    Long discountPrice = 0L;
    UsersCheckCouponDto selectedCoupon = cartDto.getCoupon();

    //선택된 쿠폰이 있다면
    if (selectedCoupon != null) {
      discountPrice = calculateDiscount(selectedCoupon, totalPrice);
      cart.setUserCouponId(selectedCoupon.getUserCouponId());
    }


    // 총 결제 금액 (주문 금액 + 배달료 - 할인금액)
    Long totalAmount = totalPrice + cartDto.getDeliveryFee() - discountPrice;
    String orderName = cart.getCartItems()
            .stream()
            .findFirst()
            .map(cartItem -> cartItem.getMenuManagement().getMenuName()) // 메뉴 이름 추출
            .orElse("Default Order Name"); // 장바구니가 비어 있을 경우 기본값


    // Redis에 멱등성 키 저장
    List<String> menuNames = cart.getCartItems().stream()
            .map(cartItem -> cartItem.getMenuManagement().getMenuName())
            .toList();

    String orderId = UUID.randomUUID().toString();

    String idempotencyKey = idempotencyKeyService.getOrCreateIdempotencyKey(userId, menuNames);

    // 결제 요청 생성
    PaymentRequestDto paymentRequest = PaymentRequestDto.builder()
            .orderId(UUID.randomUUID().toString())
            .method(method)
            .orderName(orderName)
            .amount(totalAmount)
            .failUrl(tossPaymentConfig.getFailUrl())
            .successUrl(tossPaymentConfig.getSuccessUrl())
            .build();

    // 결제 요청 수행
    PaymentResponseDto paymentResponse = paymentService.requestPayment(paymentRequest, idempotencyKey);
    /*Payment payment = paymentRepository.findByOrderId(paymentRequest.getOrderId()).orElseThrow(IllegalArgumentException::new);
    PaymentResponseDto paymentResponse = PaymentResponseDto.builder()
            .paymentKey(payment.getPaymentKey())
            .orderId(payment.getOrderId())
            .checkout(payment.getUrl())
            .amount(payment.getAmount()).build();*/

    // 최종 응답 DTO 생성
    cartDto.setStoreRequirement(storeRequirement);
    cartDto.setDeliveryRequirement(deliveryRequirement);

    // 선택된 쿠폰의 사용 여부 및 사용 날짜 저장
    if (selectedCoupon != null) {
      UserCoupon userCoupon = userCouponRepository.findById(selectedCoupon.getUserCouponId())
              .orElseThrow(() -> new IllegalArgumentException("사용자 쿠폰이 없습니다."));

      if (userCoupon.isCouponUsed()) { //사용된 쿠폰인지 검증
        throw new IllegalArgumentException("이미 사용된 쿠폰입니다.");
      }

      userCoupon.setCouponUsed(true);
      userCoupon.setUsedDate(LocalDateTime.now());
      userCouponRepository.save(userCoupon);
    }
    cart.setOrderId(orderId);
    cart.setStoreRequirement(storeRequirement);
    cart.setDeliveryRequirement(deliveryRequirement);
    cartRepository.save(cart); // 장바구니 업데이트
    return UsersCartResponseDto.builder()
            .cartId(cart.getCartId())
            .userId(cart.getUser().getUserId())
            .storeName(cartDto.getStoreName())
            .cartItems(cartItemDtos)
            .deliveryFee(cartDto.getDeliveryFee())
            .discountPrice(discountPrice)
            .totalAmount(totalAmount)
//            .paymentInfo(paymentResponse)
            .storeRequirement(storeRequirement)
            .deliveryRequirement(deliveryRequirement)
            .coupon(selectedCoupon)
            .build();
  }

  /**
   * 쿠폰을 사용한 할인 금액 계산하는 메소드.
   * @param coupon 선택된 쿠폰.
   * @param totalPrice 할인 전 총금액.
   * @return 할인금액
   */
  private Long calculateDiscount(UsersCheckCouponDto coupon, Long totalPrice) {
    if (totalPrice < coupon.getMinOrderPrice()) {
      return 0L; // 최소 주문 금액 조건 미충족 시 할인 없음
    }

    //할인 타입이 정률일때
    if (coupon.getDiscountType().equals(DiscountType.PERCENT)) {
      Long calculatedDiscount = totalPrice * coupon.getDiscountPrice() / 100;
      return Math.min(calculatedDiscount, coupon.getMaximumDiscount()); // 최대 할인 금액 제한
    }
    //할인 타입이 정액일때
    else if (coupon.getDiscountType().equals(DiscountType.FIXED)) {
      return coupon.getDiscountPrice();
    }

    return 0L;

  }



}
