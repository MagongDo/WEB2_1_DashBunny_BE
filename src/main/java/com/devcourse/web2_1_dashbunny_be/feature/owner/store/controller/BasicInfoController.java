package com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.BasicInfoListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.UpdateBasicInfoRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *가게 기본 정보 클래스.
 */
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class BasicInfoController {

  private final StoreService storeService;

  /**
   * 특정 가게의 기본 정보를 반환합니다.
   */
  @GetMapping("/basic-info/{storeId}")
  public ResponseEntity<BasicInfoListResponseDto> getBasicInfo(
          @PathVariable("storeId") String storeId) {
    BasicInfoListResponseDto basicInfoResponse = storeService.findBasicInfo(storeId);
    return ResponseEntity.ok(basicInfoResponse);
  }

  /**
 * 가게 기본 정보 수정을 위한 요청.
 */
  @PatchMapping("/basic-info/{storeId}")
  public ResponseEntity<String> updateBasicInfo(
          @PathVariable("storeId") String storeId,
          @RequestBody UpdateBasicInfoRequestDto updateBasicInfo) {
    storeService.updateBasicInfo(updateBasicInfo);
    return ResponseEntity.ok("정보 수정이 완료되었습니다.");
  }

}


