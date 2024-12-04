package com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.OrderItem;
import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.OrderStatus;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.controller.dto.*;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final Validator validator;
  private final OrdersRepository ordersRepository;
  private final MenuRepository menuRepository;
  private final SimpMessagingTemplate messageTemplate;
  private static final String ERROR_TOPIC = "/topic/order/error";

  /**
   * 사용자의 주문 요청을 처리합니다.
   * Async 어노테이션을 사용 하지 않고 .supplyAsync 사용하여 명시적으로 비동기 처리를 진행하였습니다.
   */
  @Transactional
  public CompletableFuture<Orders> creatOrder(OrderInfoRequestDto orderInfoRequestDto) {
    return CompletableFuture.supplyAsync(() -> {
      User user = validator.validateUserId(orderInfoRequestDto.getUserPhone());
      Orders orders = orderInfoRequestDto.toEntity(orderInfoRequestDto.getOrderItems(), menuRepository, user);
      List<OrderItem> orderItems = orders.getOrderItems();
      Map<Long, MenuManagement> menuCache = getMenuCache(orderItems);
      // 재고 확인 및 재고 차감
      soldOutCheck(orderItems, menuCache);
      updateMenuStock(orderItems, menuCache, 1);
      // 주문 저장
      ordersRepository.save(orders);
      // 메시지 알림
      StoreOrderAlarmResponseDto responseDto = StoreOrderAlarmResponseDto.fromEntity(orders);
      String orderTopic = String.format("/topic/storeOrder/" + orders.getOrderId());
      messageTemplate.convertAndSend(orderTopic, responseDto);
      log.info("사장님 알람 전송" + responseDto);

      return orders;
    });
  }

  /**
  * OrderItem 리스트를 기반으로 메뉴 ID를 키로,
  * 메뉴 객체(MenuManagement)를 값으로 하는 맵을 생성.
  *  메뉴 ID에 대해 중복으로 조회하지 않도록 캐싱 기능을 제공하기 위한 메서드.
  */
  public Map<Long, MenuManagement> getMenuCache(List<OrderItem> orderItems) {
    Map<Long, MenuManagement> menuCache = new HashMap<>();
    for (OrderItem orderItem : orderItems) {
      Long key = orderItem.getMenu().getMenuId();
      try {
        MenuManagement menuManagement = validator.validateMenuId(key);
        menuCache.put(key, menuManagement);
      } catch (Exception e) {
        log.error("키 {}에 대한 메뉴 검증 중 오류 발생", key, e);
      }
    }
    return menuCache;
  }

  /**
   * 재고 확인 메서드.
   * 사용자가 주문한 메뉴의 수량과 기존 메뉴의 수량을 비교합니다.
   * 이때 사용자가 주문한 메뉴의 수량이 재고보다 크거나 같을때 true를 반환하고 아닐경우에 에러를 던집니다.
   */
  public void soldOutCheck(List<OrderItem> orderItems, Map<Long, MenuManagement> menuCache) {
    boolean menuStock = orderItems.stream()
                .allMatch(
                        orderItem -> {
                          MenuManagement menu = menuCache.get(orderItem.getMenu().getMenuId());
                          return menu.getMenuStock() >= orderItem.getQuantity();
                        });
    if (!menuStock) {
      throw new IllegalStateException("재고가 부족합니다.");
    }
  }

  /**
  * 재고 확인이 된 메뉴에 한에서 db에 재고를 업데이트 합니다.
  */
  public void updateMenuStock(List<OrderItem> orderItems, Map<Long, MenuManagement> menuCache, int type) {
    orderItems.forEach(orderItem -> {
      MenuManagement menu = menuCache.get(orderItem.getMenu().getMenuId());

  if(type == 1) {
        menu.setMenuStock(menu.getMenuStock() - orderItem.getQuantity());
  }
  else if (type == 0) {
  menu.setMenuStock(menu.getMenuStock() + orderItem.getQuantity());
  }
  menuRepository.save(menu);
    });
  }

  @Async
  @Transactional
  public CompletableFuture<AcceptOrdersResponseDto> acceptOrder(OrderAcceptRequestDto acceptRequestDto) {
    Orders orders = validator.validateOrderId(acceptRequestDto.getOrderId());
    orders.setOrderStatus(OrderStatus.IN_PROGRESS);
    orders.setPreparationTime(acceptRequestDto.getPreparationTime());
    ordersRepository.save(orders);
    AcceptOrdersResponseDto responseDto = AcceptOrdersResponseDto.fromEntity(orders);
    return CompletableFuture.completedFuture(responseDto);
  }


  @Async
  @Transactional
  public CompletableFuture<DeclineOrdersResponseDto> declineOrder(OrderDeclineRequestDto declineRequestDto) {
    Orders orders = validator.validateOrderId(declineRequestDto.getOrderId());
    //주문 취소의 경우 재고 돌려두기
    Map<Long, MenuManagement> menuCache = getMenuCache(orders.getOrderItems());
    updateMenuStock(orders.getOrderItems(), menuCache, 0);
    orders.setOrderStatus(OrderStatus.DECLINED);
    ordersRepository.save(orders);

    DeclineOrdersResponseDto responseDto = DeclineOrdersResponseDto
            .fromEntity(orders, declineRequestDto.getDeclineReasonType());
    return  CompletableFuture.completedFuture(responseDto);
  }
}
