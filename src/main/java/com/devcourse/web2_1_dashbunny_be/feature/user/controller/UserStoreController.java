package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserStoreController {

    private final UsersStoreService usersStoreService;

    @GetMapping("/stores/{category}")
    public ResponseEntity<List<UsersStoreListResponseDto>> getUsersByCategory(@PathVariable String category,
                                                                             Principal principal,
                                                                             @RequestParam String address) {
        List<UsersStoreListResponseDto> storeList= usersStoreService.usersStoreListResponse(principal.getName(), category, address);
        return ResponseEntity.ok(storeList);
    }

    @PostMapping("/stores/checking")
    public ResponseEntity<Void> getUsersStoreChecking(@RequestParam String address,
                                                      Principal principal) {
        if (!usersStoreService.checkRedisData(principal.getName(),address)) {
            // Redis 키가 없으면 데이터를 새로 추가
            usersStoreService.redisAddStoreList(principal.getName(), address);
        }
        return ResponseEntity.ok().build();
    }
}
