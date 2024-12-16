package com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.CreateOperationInfoResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.OperationInfoListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.UpdatePauseTimeRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 가게 운영정보 컨트롤러.
 */
@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class OperationInfoController {

  private final StoreService storeService;

  /**
  * 기본 운영정보 조회 api.
  */
  @GetMapping("/operation-info/{storeId}")
  public ResponseEntity<OperationInfoListResponseDto> getOperationInfo(
          @PathVariable("storeId") String storeId) {
    log.info("Get operation info for store {}", storeId);
    OperationInfoListResponseDto operationInfo = storeService.findOperationInfo(storeId);
    return ResponseEntity.ok(operationInfo);
    // 운영 정보 시작 시간 정지 시가 ㄴ
  }

  /**
   * 운영정보 추가 및 저장 api.
   */
  @PostMapping("operation-info/{storeId}")
  public ResponseEntity<String> addOperationInfo(
          @PathVariable("storeId") String storeId,
          @RequestBody CreateOperationInfoResponseDto operationInfoDto) {
    storeService.addOperationInfo(storeId, operationInfoDto);
    return ResponseEntity.ok("운영 정보가 성공적으로 저장되었습니다.>");
  }

  /**
   *영업 일시 중지 상태 업데이트를 위한 api.
   */
  @PatchMapping("operation-pause/{storeId}")
  public ResponseEntity<String> updatePauseTime(
          @PathVariable("storeId") String storeId,
          @RequestBody UpdatePauseTimeRequestDto pauseTimeDto) {
    storeService.updatePauseTime(storeId, pauseTimeDto);
    return ResponseEntity.ok("영업이 일시 중지되었습니다.");
  }

  /**
   * 영업 일시 중지 해제를 위한 api.
   */
  @PatchMapping("/operation-resume/{storeId}")
  public ResponseEntity<String> updateResumeTime(
          @PathVariable("storeId") String storeId) {
    storeService.updateResumeTime(storeId);
    return ResponseEntity.ok("영업이 다시 시작되었습니다.");
  }
}
