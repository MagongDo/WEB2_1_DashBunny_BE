package com.devcourse.web2_1_dashbunny_be.feature.owner.shorts.controller;


import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts.ShortsCreateRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.shorts.ShortsRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.shorts.service.ShortsService;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/owner/shorts")
@RequiredArgsConstructor
public class ShortsController {

    private final ShortsService shortsService;
    private final UserService userService;

    /**
     * 쇼츠Url 등록 및 수정 하는 엔드포인트입니다.
     * 응답 코드: 200(OK), 400(BAD_REQUEST), 403(FORBIDDEN)
     * @param shortsRequestDto ShortsRequestDto 가 포함된 요청 본문
     *                   - userId;
     *                   - address; // 사용자 주소
     * @return 회원가입 성공 메시지 또는 에러 메시지를 포함한 ResponseEntity
     */
    @PostMapping("/nearby-store")
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

    /**
     * 쇼츠Url 등록 및 수정 하는 엔드포인트입니다. 코드 201(CREATED), 400(BAD_REQUEST)
     *
     * @param shortsCreateRequestDto ShortsCreateRequestDto 가 포함된 요청 본문
     *                   - url;     // URL
     *                   - storeId; // 가게 ID
     *                   - menuId;  // 메뉴 ID
     * @return 쇼츠 URL 등록/수정 성공 메시지 또는 에러 메시지를 포함한 ResponseEntity
     */
    @PostMapping("/update/shortsUrl")
    public ResponseEntity<?> updateShortsUrl(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestBody ShortsCreateRequestDto shortsCreateRequestDto) {
        try {
            // 현재 인증된 사용자 가져오기 (필요 시)
            User currentUser = userService.getCurrentUser(authorizationHeader);
            log.info("updateShortsUrl - Current userId: {}", currentUser.getUserId());

            // 요청 데이터 유효성 검사
            if (shortsCreateRequestDto.getUrl() == null || shortsCreateRequestDto.getUrl().isEmpty()) {
                log.warn("URL이 누락되었습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("URL이 누락되었습니다.");
            }
            if (shortsCreateRequestDto.getStoreId() == null) {
                log.warn("가게 ID가 누락되었습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("가게 ID가 누락되었습니다.");
            }
            if (shortsCreateRequestDto.getMenuId() == null) {
                log.warn("메뉴 ID가 누락되었습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("메뉴 ID가 누락되었습니다.");
            }
            // 쇼츠 URL 업데이트 또는 등록
            shortsService.updateShortsUrl(shortsCreateRequestDto);

            log.info("쇼츠 URL 업데이트 성공: {}", shortsCreateRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(shortsCreateRequestDto);
        } catch (Exception e) {
            // 일반 예외 처리
            log.error("쇼츠 URL 업데이트 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("쇼츠 URL 업데이트에 실패했습니다.");
        }
    }
}
