package com.devcourse.web2_1_dashbunny_be.feature.owner.shorts.controller;


import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts.ShortsRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.shorts.service.ShortsService;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/shorts")
@RequiredArgsConstructor
public class ShortsController {

    private final ShortsService shortsService;

    @PostMapping("/nearby/shorts")
    public ResponseEntity<List<UsersStoreListResponseDto>> getNearbyShorts(@RequestBody ShortsRequestDto shortsRequestDto) {

        List<UsersStoreListResponseDto> listNearbyShorts = shortsService.getNearbyStoresShorts(shortsRequestDto);

        int limit = 10;
        for (int i = 0; i < listNearbyShorts.size() && i < limit; i++) {
            UsersStoreListResponseDto dto = listNearbyShorts.get(i);
            System.out.print("Store ID: " + dto.getStoreId());
            System.out.println("  |  Store Name: " + dto.getStoreName());
        }
        log.info("listNearbyShorts size : {}", listNearbyShorts.size());
        return ResponseEntity.ok(listNearbyShorts);
    }

}
