package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UsersStoreService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Log4j2
public class UserStoreController {

  private final UsersStoreService usersStoreService;
  private final UserService userService;

  @GetMapping("/stores/{category}")
  public ResponseEntity<List<UsersStoreListResponseDto>> getUsersByCategory(@PathVariable String category, @RequestParam String address) {
    User currentUser = userService.getCurrentUser();
    List<UsersStoreListResponseDto> storeList = usersStoreService.usersStoreListResponse(currentUser.getPhone(), address, category);
    return ResponseEntity.ok(storeList);
  }

  @PostMapping("/stores/checking")
  public ResponseEntity<Void> getUsersStoreChecking(@RequestParam String address
                                                    ) {
    User currentUser = userService.getCurrentUser();
    log.info(userService.getCurrentUser());
    if (!usersStoreService.checkRedisData(currentUser.getPhone(), address)) {
      // Redis 키가 없으면 데이터를 새로 추가
      usersStoreService.redisAddStoreList(currentUser.getPhone(), address);
    }

    return ResponseEntity.ok().build();
  }

  @GetMapping("/stores/details")
  public ResponseEntity<UsersStoreResponseDto> getUsersDetailPage(@RequestParam String storeId) {
    User currentUser = userService.getCurrentUser();
    log.info(userService.getCurrentUser());
    return ResponseEntity.ok(usersStoreService.getStoreDetails(currentUser.getUserId(), storeId));
  }
}
