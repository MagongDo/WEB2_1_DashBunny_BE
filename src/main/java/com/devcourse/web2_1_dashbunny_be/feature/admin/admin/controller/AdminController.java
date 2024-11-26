//package com.devcourse.web2_1_dashbunny_be.feature.admin.admin.controller;
//
//import com.devcourse.web2_1_dashbunny_be.feature.admin.admin.dto.AdminInfoResponseDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/admin")
//public class AdminController {
//    @GetMapping("/info")
//    public ResponseEntity<AdminInfoResponseDto> getAdminInfo(@AuthenticationPrincipal UserDetails userDetails) {
//        String adminName = userDetails.getUsername(); // 로그인된 사용자 이름 가져오기
//        AdminInfoResponseDto response = new AdminInfoResponseDto(adminName, "관리자");
//        return ResponseEntity.ok(response);
//    }
//}
