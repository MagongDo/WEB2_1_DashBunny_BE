package com.devcourse.web2_1_dashbunny_be.feature.admin.store.controller;


import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreCreateRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreListRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreApplicationService;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreRequestController {
    private final StoreManagementService storeManagementService;
    private final StoreApplicationService storeApplicationService;

    // 사장님 - 가게 등록 신청
    @PostMapping("/create")
    public ResponseEntity<String> createStore(@RequestBody StoreCreateRequestDTO storeCreateRequestDTO) {
        storeManagementService.create(storeCreateRequestDTO);
        return ResponseEntity.ok("가게 등록 승인 요청을 성공했습니다.");
    }

    //사장님 - 가게 등록 재신청
    @PostMapping("/recreate/{storeId}")
    public ResponseEntity<String> createStore(@PathVariable String storeId, @RequestBody StoreCreateRequestDTO storeCreateRequestDTO) {
        storeManagementService.reCreate(storeId, storeCreateRequestDTO);
        return ResponseEntity.ok("가게 등록 재승인 요청을 성공했습니다.");
    }

    // 사장님 - 가게 폐업 신청
    @PostMapping("/closure/{storeId}")
    public ResponseEntity<String> closeStore(@PathVariable String storeId) {
        storeManagementService.close(storeId);
        return ResponseEntity.ok("가게 폐업 승인 요청을 성공했습니다.");
    }

    // 관리자 - 가게 등록 승인
    @PutMapping("/approve/{storeId}")
    public ResponseEntity<String> approveCreatedStore(@PathVariable String storeId) {
        storeApplicationService.approve(storeId);
        return ResponseEntity.ok("가게 등록을 승인했습니다.");
    }


    // 관리자 - 가게 등록 거절
    @PutMapping("/reject/{storeId}")
    public ResponseEntity<String> rejectCreatedStore(@PathVariable String storeId, @RequestBody String reason) {
        storeApplicationService.reject(storeId, reason);
        return ResponseEntity.ok("가게 등록을 거절했습니다. 사유: " + reason);
    }

    // 관리자 - 가게 폐업 승인
    @PutMapping("/closure/approve/{storeId}")
    public ResponseEntity<String> approveClosedStore(@PathVariable String storeId) {
        storeApplicationService.close(storeId);
        return ResponseEntity.ok("가게 폐업 신청을 승인했습니다.");
    }

    //가게 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<AdminStoreRequestDTO> getStore(@PathVariable String storeId) {
        AdminStoreRequestDTO adminStoreRequestDTO =storeApplicationService.getStore(storeId);
        return ResponseEntity.ok().body(adminStoreRequestDTO);
    }

    //가게 목록 조회
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdminStoreListRequestDTO>> getAllStores() {
        List<AdminStoreListRequestDTO> stores=storeApplicationService.getStores();
        return ResponseEntity.ok().body(stores);
    }



}
