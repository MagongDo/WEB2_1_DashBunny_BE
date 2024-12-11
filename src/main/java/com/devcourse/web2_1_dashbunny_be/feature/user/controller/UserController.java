
package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.config.s3.FileUploadService;
import com.devcourse.web2_1_dashbunny_be.domain.user.PasswordResetRequest;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts.ShortsRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.shorts.service.ShortsService;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersWishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final FileUploadService fileUploadService;
  private final UsersWishListService usersWishListService;
  private final ShortsService shortsService;

    /**
     * 사용자의 프로필 사진을 업로드하는 엔드포인트입니다.
     *
     * @param profileImage 업로드된 파일
     * @return 업로드 결과를 포함한 ResponseEntity
     */

    @PostMapping(value = "/upload-profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("profileImage") MultipartFile profileImage,
                                                  @RequestHeader("Authorization") String authorizationHeader) {

        // 파일이 비어있는지 확인
        if (profileImage.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
        }
        try {
            String fileUrl = fileUploadService.uploadFile(profileImage, "profileImage");
            log.info("uploadProfilePicture : {}", fileUrl);

            userService.updateProfileImageUrl(authorizationHeader, fileUrl);

            return ResponseEntity.ok("프로필 이미지가 성공적으로 업데이트되었습니다.");
        } catch (MultipartException e) {
            return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

  // 닉네임 변경
  @PostMapping("/update-name")
  public ResponseEntity<String> updateName(@RequestHeader("Authorization") String authorizationHeader,
                                      @RequestBody User user) {
    try {
      User updateName = userService.updateName(authorizationHeader, user.getName());
      return ResponseEntity.ok("변경된 닉네임 : " + updateName.getName());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("닉네임 변경 실패: " + e.getMessage());
    }
  }

  // 비밀번호 변경
  @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody PasswordResetRequest request) {
    try {
      userService.resetPassword(
              authorizationHeader, request.getPhone(), request.getVerificationCode(), request.getNewPassword());
      return ResponseEntity.ok("비밀번호 변경 성공");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("비밀번호 변경 실패: " + e.getMessage());
    }
  }

  // 회원탈퇴
  @PostMapping("/withdraw")
  public ResponseEntity<String> withdrawUser(@RequestHeader("Authorization") String authorizationHeader) {
    try {
      userService.withdrawUser(authorizationHeader);
      return ResponseEntity.ok("회원탈퇴 성공");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("회원탈퇴 실패: " + e.getMessage());
    }
  }

  @GetMapping("/currentUser")
  public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    log.info("getCurrentUser : " + currentUser);
    return ResponseEntity.ok(currentUser);
  }

  @PostMapping("/wishModification")
  public ResponseEntity<Void> getWishModification(@RequestParam String storeId,
                                                  @RequestHeader("Authorization") String authorizationHeader) {
    log.info("Entered wishModification with storeId: {}", storeId);
    User currentUser = userService.getCurrentUser(authorizationHeader);
    log.info("getCurrentUser : " + currentUser);
    usersWishListService.getUsersWishModification(currentUser.getUserId(), storeId);
    log.info("getCurrentUser : " + currentUser.getUserId());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/wishList")
  public ResponseEntity<List<UsersStoreListResponseDto>> getWishList(@RequestHeader("Authorization") String authorizationHeader) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    return ResponseEntity.ok(usersWishListService.getUsersWishList(currentUser.getUserId()));
  }

  /**
   * 쇼츠Url 등록 및 수정 하는 엔드포인트입니다.
   * 응답 코드: 200(OK), 400(BAD_REQUEST), 403(FORBIDDEN)
   * @param shortsRequestDto ShortsRequestDto 가 포함된 요청 본문
   *                   - userId;
   *                   - address; // 사용자 주소
   * @return 회원가입 성공 메시지 또는 에러 메시지를 포함한 ResponseEntity
   */
  @PostMapping("/shorts/nearby-store")
  public ResponseEntity<?> getNearbyShorts(@RequestHeader("Authorization") String authorizationHeader,
                                           @RequestBody ShortsRequestDto shortsRequestDto) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    log.info("getNearbyShorts - Current userId: {}, Request userId: {}",
            currentUser.getUserId(), shortsRequestDto.getUserId());

    if (!currentUser.getUserId().equals(shortsRequestDto.getUserId())) {
      log.warn("사용자 ID가 일치하지 않습니다.: currentUserId={}, requestUserId={}",
              currentUser.getUserId(), shortsRequestDto.getUserId());
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
              .body("사용자 ID가 일치하지 않습니다.");
    }

    List<UsersStoreListResponseDto> listNearbyShorts = shortsService.getNearbyStoresShorts(shortsRequestDto);
    log.info("주위 가게 쇼츠 개수 {}", listNearbyShorts.size());
    return ResponseEntity.ok(listNearbyShorts);
  }


}

