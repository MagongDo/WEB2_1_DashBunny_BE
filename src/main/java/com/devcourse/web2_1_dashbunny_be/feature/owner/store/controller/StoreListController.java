package com.devcourse.web2_1_dashbunny_be.feature.owner.store.controller;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.store.StoreManagementListDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 내 가게 관리를 위한 요청, 응답 컨트롤러.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreListController {

  private final StoreService storeService;

  /**
  * 내 가게 리스트 조회를 위한 api controller.
  */
  @GetMapping("/store-list")
  public ResponseEntity<List<StoreManagementListDto>> allStoreList() {
    String phone = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("user{}",phone);
    List<StoreManagementListDto> storeDtoList = storeService.findAllStore(phone);
    log.info("list{}",storeDtoList);
    return ResponseEntity.ok(storeDtoList);
  }

}
