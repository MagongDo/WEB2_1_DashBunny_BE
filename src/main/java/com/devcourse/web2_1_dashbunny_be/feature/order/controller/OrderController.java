package com.devcourse.web2_1_dashbunny_be.feature.order.controller;

import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.*;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.user.UserOrderInfoRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.order.service.OrderService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 사용자로 부터 주문 요청을 받고 처리하는 controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

  private final String ERROR_TOPIC = "/topic/order/error";
  private final SimpMessagingTemplate messageTemplate;
  private final OrderService orderService;
  private final UserService userService;

/*  *//**
   * 사용자에게 주문 요청이 옴과 동시에 주문 접수 요청에 대한 알람을 보냅니다.
   */
  @PostMapping("/create")
  public CompletableFuture<ResponseEntity<String>> creatOrder(
          @RequestBody OrderInfoRequestDto orderInfoRequestDto) {
    return orderService.creatOrder(orderInfoRequestDto)
            .thenApply(order -> {
              String topic = String.format("/topic/userOrder/%s/%s",
                                            orderInfoRequestDto.getStoreId(), order.getOrderId());
              messageTemplate.convertAndSend(topic, "주문 접수 중");
              return ResponseEntity.ok("주문 접수 요청에 성공했습니다." + order.getOrderStatus());
            })
    .exceptionally(ex -> {
        log.error("주문 처리 중 예외 발생", ex);
        messageTemplate.convertAndSend(ERROR_TOPIC, "주문 접수 실패");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("주문 처리 중 문제가 발생했습니다: " + ex.getMessage());
    });
  }

  /**
  * 사장님 : 주문 수락.
  */
  @PostMapping("/{orderId}/accept")
  public CompletableFuture<ResponseEntity<AcceptOrdersResponseDto>> acceptOrder(
          @PathVariable("orderId") Long orderId,
          @RequestBody OrderAcceptRequestDto acceptRequestDto) {
    return orderService.acceptOrder(acceptRequestDto)
              .thenApply (acceptOrdersResponseDto -> {
                String topic = String.format("/topic/userOrder/%s/%s",
                                              acceptOrdersResponseDto.getStoreId(), orderId);
                messageTemplate.convertAndSend(topic, "메뉴가 접수 되었습니다.");
                return ResponseEntity.ok(acceptOrdersResponseDto);
              });
  }

  /**
  * 사장님 : 주문 거절.
  */
  @PostMapping("/{orderId}/decline")
  public CompletableFuture<ResponseEntity<DeclineOrdersResponseDto>> declineOrder(
          @PathVariable("orderId") Long orderId,
          @RequestBody OrderDeclineRequestDto declineRequestDto) {
    return orderService.declineOrder(declineRequestDto)
              .thenApply (declineOrdersResponseDto -> {
                String topic = String.format("/topic/userOrder/%s/%s",
                                             declineOrdersResponseDto.getStoreId(), orderId);
                messageTemplate.convertAndSend(topic, "주문이 취소 되었습니다"
                                           + declineOrdersResponseDto.getDeclineReasonType().getDescription());
                return ResponseEntity.ok(declineOrdersResponseDto);
              });
  }


@GetMapping("/list")
  public ResponseEntity<List<UserOrderInfoRequestDto>> listOrders(@RequestHeader("Authorization") String authorizationHeader) {
    User currentUser = userService.getCurrentUser(authorizationHeader);
    List<UserOrderInfoRequestDto> userOrderLost = orderService.getUserOrderInfoList(currentUser.getPhone());
    return ResponseEntity.ok(userOrderLost);
}
}
