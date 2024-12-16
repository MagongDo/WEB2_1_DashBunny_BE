package com.devcourse.web2_1_dashbunny_be.feature.admin.admin.controller;

import com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto.AdminInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto.CategoryCountDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto.UserInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.admin.service.CategoryCountService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
  private final UserService userService;
  private final CategoryCountService categoryCountService;
  @GetMapping("/info")
  public ResponseEntity<AdminInfoResponseDto> getAdminInfo(@AuthenticationPrincipal UserDetails userDetails
  , @RequestHeader("Authorization") String authorizationHeader) {
    String phone = userService.getCurrentUser(authorizationHeader).getPhone();
    String adminName = userDetails.getUsername(); // 로그인된 사용자 이름 가져오기
    AdminInfoResponseDto response = new AdminInfoResponseDto(adminName, "관리자");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/category")
  public ResponseEntity<CategoryCountDto> getCategoryCount(){
    return ResponseEntity.ok(categoryCountService.getCategoryCount());
  }

  @GetMapping("/user")
  public ResponseEntity<UserInfoResponseDto> getUser(){

    return ResponseEntity.ok(categoryCountService.getUserOwnerCount());

  }


}
