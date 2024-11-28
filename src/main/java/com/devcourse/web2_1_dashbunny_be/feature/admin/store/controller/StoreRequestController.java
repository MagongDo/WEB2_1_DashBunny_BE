package com.devcourse.web2_1_dashbunny_be.feature.admin.store.controller;

import com.devcourse.web2_1_dashbunny_be.config.s3.FileUploadService;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreApplicationService;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreManagementService;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.CreateStoreRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.StoreService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * 사장님: 가게 등록/폐업 신청 & 관리자: 승인/거절 repository.
 */

@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreRequestController {
  private final StoreManagementService storeManagementService;
  private final StoreApplicationService storeApplicationService;
  private final FileUploadService fileUploadService;
  private final UserService userService;

  /**
   * 사장님 - 가게 등록 신청 api (POST).
   */
  @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> createStore(
          @RequestParam(name = "bannerImageFile") MultipartFile bannerImageFile,
          @RequestParam(name = "logoImageFile") MultipartFile logoImageFile,
          @RequestPart(name = "request") CreateStoreRequestDto request
          /*Principal principa*/) {

    Object userType= userService.getCurrentUser();
    User user = null;
    String providerId;

    if (userType instanceof User) {
      user = (User) userType;
    } else if (userType instanceof OAuth2User) { // OAuth2 카카오 로그인 사용자 처리
      OAuth2User oauth2User = (OAuth2User) user;
      // getName()으로 Name 값 가져오기
      // provider_id 가져옴
      providerId = oauth2User.getName();
      user = null;
    }

    log.info("user{{}",user.getName());
    try {
      log.info("Creating a new store request{}",request.toString());
      log.info("name{}",user.getName());
      String bannerImageFileUrl = fileUploadService.uploadFile(bannerImageFile,"storeBannerImage");
      String logoImageFileUrl = fileUploadService.uploadFile(logoImageFile,"storeLogoImage");

      log.info("url{}",bannerImageFileUrl);
      log.info("url{}",logoImageFileUrl);
      request.setUserName(user.getName());
      request.setStoreBannerImage(bannerImageFileUrl);
      request.setStoreLogo(logoImageFileUrl);
      // 디버그 로그 추가
      System.out.println("배너 파일 이름: " + bannerImageFile.getOriginalFilename());
      System.out.println("로고 파일 이름: " + logoImageFile.getOriginalFilename());
      System.out.println("가게 이름: " + request.getStoreName());
      System.out.println("카테고리: " + request.getCategories());
      storeManagementService.create(request);
      return ResponseEntity.ok("가게 등록 승인 요청을 성공했습니다.");
    } catch (Exception e) {

      return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
    }
  }


  /**
   * 사장님 - 가게 등록 재신청 api (POST).
   */
  @PostMapping("/recreate/{storeId}")
  public ResponseEntity<String> recreateStore(@PathVariable String storeId, @RequestBody CreateStoreRequestDto storeCreateRequestDto) {
    storeManagementService.reCreate(storeId, storeCreateRequestDto);
    return ResponseEntity.ok("가게 등록 재승인 요청을 성공했습니다.");
  }


  /**
   * 사장님 - 가게 폐업 신청 api (POST).
   */
  @PostMapping("/closure/{storeId}")
  public ResponseEntity<String> closeStore(@PathVariable String storeId) {
    storeManagementService.close(storeId);
    return ResponseEntity.ok("가게 폐업 승인 요청을 성공했습니다.");
  }



  /**
   * 관리자 - 가게 등록 승인 api (PUT).
   */
  @PutMapping("/approve/{storeId}")
  public ResponseEntity<String> approveCreatedStore(@PathVariable String storeId) {
    storeApplicationService.approve(storeId);
    return ResponseEntity.ok("가게 등록을 승인했습니다.");
  }



  /**
   * 관리자 - 가게 등록 거절 api (PUT).
   */
  @PutMapping("/reject/{storeId}")
  public ResponseEntity<String> rejectCreatedStore(@PathVariable String storeId, @RequestBody String reason) {
    storeApplicationService.reject(storeId, reason);
    return ResponseEntity.ok("가게 등록을 거절했습니다. 사유: " + reason);
  }


  /**
   * 관리자 - 가게 폐업 승인 api (PUT).
   */
  @PutMapping("/closure/approve/{storeId}")
  public ResponseEntity<String> approveClosedStore(@PathVariable String storeId) {
    storeApplicationService.close(storeId);
    return ResponseEntity.ok("가게 폐업 신청을 승인했습니다.");
  }



  /**
   * 관리자 - 가게 조회 api (GET).
   */
  @GetMapping("/{storeId}")
  public ResponseEntity<AdminStoreResponseDto> getStore(@PathVariable String storeId) {
    AdminStoreResponseDto adminStoreResponseDto = storeApplicationService.getStore(storeId);
    return ResponseEntity.ok().body(adminStoreResponseDto);
  }



  /**
   * 관리자 - 가게 목록 조회 api (GET).
   */
//
//  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<List<AdminStoreListResponseDto>> getAllStores() {
//
//    List<AdminStoreListResponseDto> stores = storeApplicationService.getStores();
//
//    return  ResponseEntity.ok().body(stores);
//
//  }



  /**
   * 관리자 - 가게 상태에 따른 목록 조회 api (GET).
   * @param status 가게 상태
   * @param page 페이지 번호
   * @param size 한 페이지당 데이터 수
   * @return 페이징된 가게 목록
   */

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<AdminStoreListResponseDto>> getStores(
          @RequestParam(defaultValue = "ENTIRE") String status,
          @RequestParam(defaultValue = "1") int page,
          @RequestParam(defaultValue = "10") int size
  ) {
    Page<AdminStoreListResponseDto> stores = storeApplicationService.getStores(status, page, size);
    return ResponseEntity.ok().body(stores);
  }



}

