package com.devcourse.web2_1_dashbunny_be.feature.admin.store.controller;

import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreResponseDto;
/*import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreCreateRequestDto;*/
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreApplicationService;
/*import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreManagementService;*/
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사장님: 가게 등록/폐업 신청 & 관리자: 승인/거절 repository.
 */
/*
@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreRequestController {
  private final StoreManagementService storeManagementService;
  private final StoreApplicationService storeApplicationService;

*/

  /**
   * 사장님 - 가게 등록 신청 api (POST).
   */
/*
  @PostMapping("/create")
  public ResponseEntity<String> createStore(@RequestBody StoreCreateRequestDto request) {
    storeManagementService.create(request);
    return ResponseEntity.ok("가게 등록 승인 요청을 성공했습니다.");
  }
*/


  /**
   * 사장님 - 가게 등록 재신청 api (POST).
   */
/*
  @PostMapping("/recreate/{storeId}")
  public ResponseEntity<String> recreateStore(@PathVariable String storeId, @RequestBody StoreCreateRequestDto storeCreateRequestDto) {
    storeManagementService.reCreate(storeId, storeCreateRequestDto);
    return ResponseEntity.ok("가게 등록 재승인 요청을 성공했습니다.");
  }
*/

/*

  */
/**
   * 사장님 - 가게 폐업 신청 api (POST).
   *//*

  @PostMapping("/closure/{storeId}")
  public ResponseEntity<String> closeStore(@PathVariable String storeId) {
    storeManagementService.close(storeId);
    return ResponseEntity.ok("가게 폐업 승인 요청을 성공했습니다.");
  }


  */
/**
   * 관리자 - 가게 등록 승인 api (PUT).
   *//*

  @PutMapping("/approve/{storeId}")
  public ResponseEntity<String> approveCreatedStore(@PathVariable String storeId) {
    storeApplicationService.approve(storeId);
    return ResponseEntity.ok("가게 등록을 승인했습니다.");
  }


  */
/**
   * 관리자 - 가게 등록 거절 api (PUT).
   *//*

  @PutMapping("/reject/{storeId}")
  public ResponseEntity<String> rejectCreatedStore(@PathVariable String storeId, @RequestBody String reason) {
    storeApplicationService.reject(storeId, reason);
    return ResponseEntity.ok("가게 등록을 거절했습니다. 사유: " + reason);
  }

  */
/**
   * 관리자 - 가게 폐업 승인 api (PUT).
   *//*

  @PutMapping("/closure/approve/{storeId}")
  public ResponseEntity<String> approveClosedStore(@PathVariable String storeId) {
    storeApplicationService.close(storeId);
    return ResponseEntity.ok("가게 폐업 신청을 승인했습니다.");
  }


  */
/**
   * 관리자 - 가게 조회 api (GET).
   *//*

  @GetMapping("/{storeId}")
  public ResponseEntity<AdminStoreResponseDto> getStore(@PathVariable String storeId) {
    AdminStoreResponseDto adminStoreResponseDto = storeApplicationService.getStore(storeId);
    return ResponseEntity.ok().body(adminStoreResponseDto);
  }

*/

//  /**
//   * 관리자 - 가게 목록 조회 api (GET).
//   */
//  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<List<AdminStoreListResponseDto>> getAllStores() {
//    List<AdminStoreListResponseDto> stores = storeApplicationService.getStores();
//    return ResponseEntity.ok().body(stores);
//  }


/*
  */
/**
   * 관리자 - 가게 상태에 따른 목록 조회 api (GET).
   * @param status 가게 상태
   * @param page 페이지 번호
   * @param size 한 페이지당 데이터 수
   * @return 페이징된 가게 목록
   */

/*

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
*/
