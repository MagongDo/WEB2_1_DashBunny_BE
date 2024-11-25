//package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service;
//
//
//import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
//import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
//import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
//import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AddAdminCouponRequestDto;
//import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponListResponseDto;
//import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponResponseDto;
//import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.ChangeAdminCouponStatusRequestDto;
//import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository.AdminCouponRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.context.ActiveProfiles;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@ActiveProfiles("test")
//class AdminCouponServiceTest {
//
//  @Mock
//  private AdminCouponRepository adminCouponRepository;
//
//  @InjectMocks
//  private AdminCouponService adminCouponService;
//
//  @BeforeEach
//  void setUp() {
//    MockitoAnnotations.openMocks(this);
//  }
//
//  @Test
//  void testSaveAdminCoupon() {
//      //GIVEN
//    AddAdminCouponRequestDto addAdminCouponRequestDto = new AddAdminCouponRequestDto(
//            "Welcome Coupon",
//            CouponType.Regula,
//            CouponStatus.ACTIVE,
//            5000L,
//            15000L,
//            100L,
//            LocalDateTime.of(2024, 12, 31, 23, 59),
//            "This is a welcome coupon"
//
//    );
//    AdminCoupon adminCoupon = addAdminCouponRequestDto.toEntity();
//    when(adminCouponRepository.save(any(AdminCoupon.class))).thenReturn(adminCoupon); // adminCouponRepository.save 메소드가 호출되었을떄 adminCoupon 객체가 반환하도록
//
//    //WHEN
//    AdminCoupon result = adminCouponService.saveAdminCoupon(addAdminCouponRequestDto);
//
//    //THEN
//    assertThat(result.getCouponName()).isEqualTo("Welcome Coupon");
//
//    verify(adminCouponRepository, times(1)).save(any(AdminCoupon.class)); // 정확히 1번 호출했는지 검증
//  }
//
//  @Test
//  void testFindAllAdminCoupons() {
//    //GIVEN
//    AdminCoupon adminCoupon = AdminCoupon.builder()
//            .couponName("Welcome Coupon")
//            .couponStatus(CouponStatus.ACTIVE)
//            .couponType(CouponType.Regula)
//            .discountPrice(5000L)
//            .minOrderPrice(15000L)
//            .expiredDate(LocalDateTime.of(2024, 12, 31, 23, 59))
//            .build();
//    when(adminCouponRepository.findAll()).thenReturn(List.of(adminCoupon));
//
//    //WHEN
//    List<AdminCouponListResponseDto> result = adminCouponService.findAllAdminCoupons();
//
//    //THEN
//    assertThat(result).hasSize(1);
//    assertThat(result.get(0).getCouponName()).isEqualTo("Welcome Coupon");
//    verify(adminCouponRepository, times(1)).findAll();
//  }
//
//  @Test
//  void testFindAdminCouponById() {
//    //GIVEN
//    String couponId = "HelloCoupon";
//    AdminCoupon adminCoupon = AdminCoupon.builder()
//            .couponId(couponId)
//            .couponName("Welcome Coupon")
//            .couponStatus(CouponStatus.ACTIVE)
//            .couponType(CouponType.Regula)
//            .discountPrice(5000L)
//            .minOrderPrice(15000L)
//            .expiredDate(LocalDateTime.of(2024, 12, 31, 23, 59))
//            .build();
//    when(adminCouponRepository.findById(couponId)).thenReturn(Optional.of(adminCoupon));
//
//    //WHEN
//    AdminCouponResponseDto result = adminCouponService.finAdminCouponById(couponId);
//
//    //THEN
//    assertThat(result.getCouponName()).isEqualTo("Welcome Coupon");
//    verify(adminCouponRepository, times(1)).findById(couponId);
//  }
//
//  @Test
//  void testFindAdminCouponStatusChange() {
//    //GIVEN
//    String couponId = "HelloCoupon";
//    AdminCoupon adminCoupon = AdminCoupon.builder()
//            .couponId(couponId)
//            .couponName("Welcome Coupon")
//            .couponStatus(CouponStatus.PENDING)
//            .couponType(CouponType.Regula)
//            .discountPrice(5000L)
//            .minOrderPrice(15000L)
//            .expiredDate(LocalDateTime.of(2024, 12, 31, 23, 59))
//            .build();
//    ChangeAdminCouponStatusRequestDto changeAdminCouponStatusRequestDto = new ChangeAdminCouponStatusRequestDto(CouponStatus.ACTIVE);
//    when(adminCouponRepository.findById(couponId)).thenReturn(Optional.of(adminCoupon));
//    when(adminCouponRepository.save(any(AdminCoupon.class))).thenReturn(adminCoupon);
//
//    //WHEN
//    AdminCoupon result = adminCouponService.finAdminCouponStatusChange(couponId, changeAdminCouponStatusRequestDto);
//
//    //THEN
//    assertThat(result.getCouponStatus()).isEqualTo(CouponStatus.ACTIVE);
//    verify(adminCouponRepository, times(1)).findById(couponId);
//    verify(adminCouponRepository, times(1)).save(any(AdminCoupon.class));
//
//  }
//
//
//
//
//}