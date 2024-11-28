/*
package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.feature.user.service.FileStorageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final FileStorageService fileStorageService;

    */
/**
     * 사용자의 프로필 사진을 업로드하는 엔드포인트입니다.
     *
     * @param file 업로드된 파일
     * @return 업로드 결과를 포함한 ResponseEntity
     *//*

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("profileImage") MultipartFile file,
                                                  HttpSession session) {

        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
        }

        try {
            // 파일 저장 및 URL 획득
            String fileUrl = fileStorageService.storeFile(file);
            log.info("파일이 성공적으로 저장되었습니다: {}", fileUrl);

            // 현재 사용자 정보 가져오기
//            SocialUser socialUser = (SocialUser) session.getAttribute(SESSION_USER_KEY);
//            if (socialUser == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증된 사용자가 아닙니다.");
//            }

            // 사용자 엔티티 업데이트
//            userService.updateProfileImageUrl(socialUser.getUserId(), fileUrl);

            return ResponseEntity.ok("프로필 사진이 성공적으로 업로드되었습니다.");
        } catch (IOException e) {
            log.error("파일 업로드 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드에 실패했습니다.");
        }
    }

}
*/
