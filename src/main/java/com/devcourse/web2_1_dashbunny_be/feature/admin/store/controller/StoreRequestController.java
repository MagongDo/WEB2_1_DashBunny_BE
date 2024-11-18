package com.devcourse.web2_1_dashbunny_be.feature.admin.store.controller;


import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreClosureRequest;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreCreateRequest;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreApplicationService;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreRequestController {
    private final StoreManagementService storeManagementService;
    private final StoreApplicationService storeApplicationService;

    // 사장님 - 가게 등록 신청
    @PostMapping("/create")
    public ResponseEntity<String> createStore(@RequestBody StoreCreateRequest storeCreateRequest) {
        storeManagementService.create(storeCreateRequest);
        return ResponseEntity.ok("가게 생성 승인 요청을 성공했습니다.");
    }

    // 사장님 - 가게 폐업 신청
    @PostMapping("/closure/{storeId}")
    public ResponseEntity<String> closeStore(@PathVariable String storeId) {
        storeManagementService.close(storeId);
        return ResponseEntity.ok("가게 폐업 승인 요청을 성공했습니다.");
    }
    //관리자 가게 등록 승인
//    @PutMapping("/approve/{applicationId}")
//    public ResponseEntity<String> ApproveCreatedStore(@PathVariable Long applicationId){
//        StoreStatus storeStatus=storeManagementService.get(storeId).getStoreStatus(); //현재 가게 상태
//        if(storeStatus.equals("PENDING")){
//            storeApplicationService.approve(storeId);
//            return ResponseEntity.ok("가게 등록을 승인했습니다.");
//        }
//            return ;
//    }
    // 관리자 - 가게 등록 승인
    @PutMapping("/approve/{storeId}")
    public ResponseEntity<String> approveCreatedStore(@PathVariable String storeId) {
        storeApplicationService.approve(storeId);
        return ResponseEntity.ok("가게 등록을 승인했습니다.");
    }
    //관리자 가게 등록 거절
//    @PutMapping("/reject/{applicationId}")
//    public ResponseEntity<String> RejectCreatedStore(@PathVariable String storeId){
//        StoreStatus storeStatus=storeManagementService.get(storeId).getStoreStatus(); //현재 가게 상태
//        if(storeStatus.equals("PENDING")){
//            storeApplicationService.reject(storeId);
//            return ResponseEntity.ok("가게 등록을 거절했습니다.");
//        }
//
//    }

    // 관리자 - 가게 등록 거절
    @PutMapping("/reject/{storeId}")
    public ResponseEntity<String> rejectCreatedStore(@PathVariable String storeId, @RequestParam String reason) {
        storeApplicationService.reject(storeId, reason);
        return ResponseEntity.ok("가게 등록을 거절했습니다. 사유: " + reason);
    }
    //관리자 가게 폐업 승인
//    @PutMapping("/closure/approve/{storeId}")
//    public ResponseEntity<String> ApproveClosedStore(@PathVariable String storeId){
//        StoreStatus storeStatus=storeManagementService.get(storeId).getStoreStatus(); //현재 가게 상태
//        if(storeStatus.equals("CLOSURE_PENDING")){
//            storeApplicationService.close(storeId);
//            return ResponseEntity.ok("가게 폐업 승인 요청을 성공했습니다.");
//
//        }
//    }

    // 관리자 - 가게 폐업 승인
    @PutMapping("/closure/approve/{storeId}")
    public ResponseEntity<String> approveClosedStore(@PathVariable String storeId) {
        storeApplicationService.close(storeId);
        return ResponseEntity.ok("가게 폐업 신청을 승인했습니다.");
    }

    //가게 조회



//    @GetMapping()
//    public ResponseEntity<StoreListView> getStore(){
//
//    }

    //승인
//    @PutMapping()
//
//    //거절
//    @PutMapping
}
