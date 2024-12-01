/*
package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.config.s3.FileUploadService;
import com.devcourse.web2_1_dashbunny_be.domain.user.PasswordResetRequest;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UserDTO;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.FileStorageService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileUploadService fileUploadService;

    */
/**
     * 사용자의 프로필 사진을 업로드하는 엔드포인트입니다.
     *
     * @param profileImage 업로드된 파일
     * @return 업로드 결과를 포함한 ResponseEntity
     *//*

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("profileImage") MultipartFile profileImage) {

        // 파일이 비어있는지 확인
        if (profileImage.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
        }
        try {
            String fileUrl = fileUploadService.uploadFile(profileImage, "profileImage");
            log.info("uploadProfilePicture : {}", fileUrl);
            // 현재 사용자 정보 가져오기
            Object currentUser = userService.getCurrentUser();
            User user = null;

            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증된 사용자가 아닙니다.");
            }

            if (currentUser instanceof User) {
                user = (User) currentUser;
            } else if (currentUser instanceof OAuth2User) { // OAuth2 카카오 로그인 사용자 처리
                OAuth2User oauth2User = (OAuth2User) currentUser;
                // getName()으로 Name 값 가져오기
                // provider_id 가져옴
                String providerId = oauth2User.getName();
                user = userService.findUserByProviderId(providerId);
            }

            userService.updateProfileImageUrl(user.getUserId(), fileUrl);

            return ResponseEntity.ok("프로필 이미지가 성공적으로 업데이트되었습니다.");
        } catch (MultipartException e) {
            return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    @GetMapping("/info")
//    public String getUserInfo() {
//        log.info("getUserInfo : " + userService.getCurrentUser());
//        return userService.getCurrentUser();
//    }

    // 닉네임 변경
    @PostMapping("/update-name")
    public ResponseEntity<?> updateName(@RequestBody User user) {
        try {
            userService.updateName(user.getName());
            return ResponseEntity.ok("닉네임 변경 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("닉네임 변경 실패: " + e.getMessage());
        }
    }

    // 비밀번호 변경
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        try {
            userService.resetPassword(request.getPhone(), request.getVerificationCode(), request.getNewPassword());
            return ResponseEntity.ok("비밀번호 변경 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("비밀번호 변경 실패: " + e.getMessage());
        }
    }

    @GetMapping("/currentUser")
    public ResponseEntity<UserDTO> getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        UserDTO user = UserDTO.toDTO(currentUser);
        return ResponseEntity.ok(user);
    }

}
*/
