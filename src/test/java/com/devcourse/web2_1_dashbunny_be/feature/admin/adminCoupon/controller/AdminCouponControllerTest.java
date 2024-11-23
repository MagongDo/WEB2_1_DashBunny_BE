package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.controller;

import com.devcourse.web2_1_dashbunny_be.config.SecurityConfig;
import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponAddRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponListRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.dto.AdminCouponStatusChangeRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository.AdminCouponRepository;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.service.AdminCouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
//@WebMvcTest(controllers = AdminCouponController.class)
@Import(SecurityConfig.class) // 시큐리티 설정 파일 추가
@WithMockUser // Mock 사용자 인증 추가
class AdminCouponControllerTest {

  //  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AdminCouponService adminCouponService;

  @Autowired
  private AdminCouponController adminCouponController; // 컨트롤러 주입

  @BeforeEach
  void setup() {
    // MockMvc 수동 생성
    mockMvc = MockMvcBuilders.standaloneSetup(adminCouponController).build();
  }

  @Test
  void addAdminCoupon() throws Exception {
    //GIVEN
    AdminCouponAddRequestDto requestDto = new AdminCouponAddRequestDto(
            "Welcome Coupon",
            CouponType.Regula,
            CouponStatus.ACTIVE,
            5000L,
            15000L,
            100L,
            LocalDateTime.of(2024, 12, 31, 23, 59),
            "This is a welcome coupon"
    );
    AdminCoupon response = requestDto.toEntity();

    when(adminCouponService.saveAdminCoupon(any(AdminCouponAddRequestDto.class))).thenReturn(response);

    //WHEN & THEN
    mockMvc.perform(post("/api/adminCoupon")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(requestDto))) // 객체를 JSON 문자열로 변환
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.couponName").value("Welcome Coupon"))
            .andExpect(jsonPath("$.couponType").value("Regula"))
            .andExpect(jsonPath("$.couponStatus").value("ACTIVE"));

    verify(adminCouponService, times(1)).saveAdminCoupon(any(AdminCouponAddRequestDto.class));
  }

  @Test
  void getAllAdminCoupon() throws Exception {
    //GIVEN
    AdminCouponListRequestDto dto = new AdminCouponListRequestDto(
            "HelloCoupon",
            "Welcome Coupon",
            CouponType.Regula,
            5000L,
            LocalDateTime.of(2024, 12, 31, 23, 59),
            CouponStatus.ACTIVE
    );
    when(adminCouponService.findAllAdminCoupons()).thenReturn(List.of(dto));

    //WHEN & THEN
    mockMvc.perform(get("/api/adminCoupon"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].couponId").value("HelloCoupon"))
            .andExpect(jsonPath("$[0].couponName").value("Welcome Coupon"));

    verify(adminCouponService, times(1)).findAllAdminCoupons();

  }

  @Test
  void getAdminCoupon() throws Exception {
    //GIVEN
    String couponId = "HelloCoupon";
    AdminCouponRequestDto dto = new AdminCouponRequestDto(
            "HelloCoupon",
            "Welcome Coupon",
            CouponType.Regula,
            "This is a welcome coupon",
            5000L,
            15000L,
            100L,
            LocalDateTime.of(2024, 12, 31, 23, 59),
            CouponStatus.ACTIVE
    );
    when(adminCouponService.finAdminCouponById(couponId)).thenReturn(dto);

    //WHEN & THEN
    mockMvc.perform(get("/api/adminCoupon/{couponId}", couponId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.couponId").value(couponId))
            .andExpect(jsonPath("$.couponName").value("Welcome Coupon"));

    verify(adminCouponService, times(1)).finAdminCouponById(couponId);
  }

  @Test
  void updateAdminCouponStatus() throws Exception {
    //GIVEN
    String couponId = "HelloCoupon";
    AdminCoupon adminCoupon = new AdminCoupon(
            "HelloCoupon",
            "Welcome Coupon",
            5000L,
            15000L,
            LocalDateTime.of(2024, 12, 31, 23, 59),
            CouponStatus.PENDING,
            CouponType.Regula,
            100L,
            "This is a welcome coupon"
    );
    AdminCouponStatusChangeRequestDto request = new AdminCouponStatusChangeRequestDto(CouponStatus.ACTIVE);
    adminCoupon.setCouponStatus(CouponStatus.ACTIVE);

    when(adminCouponService.finAdminCouponStatusChange(eq(couponId), any(AdminCouponStatusChangeRequestDto.class))).thenReturn(adminCoupon); //eq는 특정 값이 매칭되도록 하는 Mockito의 Matcher

    //WHEN & THEN
    mockMvc.perform(put("/api/adminCoupon/{couponId}", couponId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.couponStatus").value("ACTIVE"));

    verify(adminCouponService, times(1)).finAdminCouponStatusChange(eq(couponId), any(AdminCouponStatusChangeRequestDto.class));

  }


}