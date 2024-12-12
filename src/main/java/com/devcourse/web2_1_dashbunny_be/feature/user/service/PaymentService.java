package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.config.TossPaymentConfig;
import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.domain.user.Payment;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.OrderInfoRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.order.controller.dto.OrderItemDto;
import com.devcourse.web2_1_dashbunny_be.feature.order.repository.OrdersRepository;
import com.devcourse.web2_1_dashbunny_be.feature.order.service.OrderService;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.PaymentRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
  private final String ERROR_TOPIC = "/topic/order/error";
  private final TossPaymentConfig tossPaymentsConfig;
  private final PaymentRepository paymentRepository;
  private final RestTemplate restTemplate;
  private final RestClient restClient;
  private final UsersCartRepository usersCartRepository;
  private final OrdersRepository ordersRepository;
  private final OrderService orderService;
  private final SimpMessagingTemplate messageTemplate;

  /**
   * 결제 준비 요청
   */
  @Transactional
  public PaymentResponseDto requestPayment(PaymentRequestDto requestDto, String idempotencyKey)  {
    // DB에 결제 요청 정보 저장 (status: READY)
    Payment payment = Payment.builder()
            .orderId(requestDto.getOrderId())
            .orderName(requestDto.getOrderName())
            .amount(requestDto.getAmount())
            .method(requestDto.getMethod())
            .status("READY")
            .build();
    paymentRepository.save(payment);

    String url = tossPaymentsConfig.getApiBaseUrl() + "/payments";

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + encodeToBase64(tossPaymentsConfig.getSecretKey() + ":"));
    headers.add("Content-Type", "application/json");
    headers.add("Idempotency-Key", idempotencyKey);

  /*  HttpEntity<PaymentRequestDto> entity = new HttpEntity<>(requestDto, headers);
    ResponseEntity<PaymentResponseDto> response = restTemplate.exchange(
            url, HttpMethod.POST, entity, PaymentResponseDto.class
    );
    PaymentResponseDto responseBody = response.getBody();
    */
    PaymentResponseDto response = restClient.post()
            .uri(url)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .body(requestDto)
            .retrieve()
            .body(PaymentResponseDto.class);


    payment.setPaymentKey(response.getPaymentKey());
    paymentRepository.save(payment);
    return response;

  }

  /**
   * 결제 승인 요청
   */
  public PaymentApproveResponseDto approvePayment(PaymentApproveRequestDto approveRequest) {
    String url = tossPaymentsConfig.getApiBaseUrl() + "/payments/" + approveRequest.getPaymentKey();

    // HTTP 헤더 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + encodeToBase64(tossPaymentsConfig.getSecretKey() + ":"));
    headers.add("Content-Type", "application/json");


/*    // HTTP 요청 엔티티 생성
    HttpEntity<PaymentApproveRequestDto> entity = new HttpEntity<>(approveRequest, headers);*/
    try {
      // REST API 호출
     /* ResponseEntity<PaymentApproveResponseDto> response = restTemplate.exchange(
              url, HttpMethod.POST, entity, PaymentApproveResponseDto.class
      );
      // 응답 본문 처리
      PaymentApproveResponseDto responseBody = response.getBody();
      */
      PaymentApproveResponseDto responseBody = restClient.post()
              .uri(url)
              .headers(httpHeaders -> httpHeaders.addAll(headers))
              .body(approveRequest)
              .retrieve()
              .body(PaymentApproveResponseDto.class);



      if (responseBody == null) {
        throw new RuntimeException("PaymentApproveResponseDto is null");
      }


      // 결제 승인 후 처리
      Payment payment = paymentRepository.findByOrderId(responseBody.getOrderId())
              .orElseThrow(() -> new RuntimeException("Payment not found"));
      payment.setPaymentKey(responseBody.getPaymentKey());
      payment.setStatus(responseBody.getStatus());
      paymentRepository.save(payment);


      // 성공적인 결제의 경우 카트 비우기
      if ("DONE".equals(responseBody.getStatus())) {
        Cart cart = usersCartRepository.findByOrderId(approveRequest.getOrderId());

        List<OrderItemDto> orderItemDtos = cart.getCartItems().stream()
                .map(OrderItemDto::toDto)
                .toList();

        OrderInfoRequestDto orders = OrderInfoRequestDto.builder()
                .storeId(cart.getStoreId())
                .paymentId(cart.getOrderId())
                .userPhone(cart.getUser().getPhone())
                .orderItems(orderItemDtos)
                .orderDate(LocalDateTime.now())
                .deliveryAddress(cart.getUser().getAddress())
                .detailDeliveryAddress(cart.getUser().getDetailAddress())
                .storeNote(cart.getStoreRequirement())
                .riderNote(cart.getDeliveryRequirement())
                .totalAmount(cart.getTotalPrice())
                .build();

        orderService.creatOrder(orders)
                .thenApply(order -> {
                  String topic = String.format("/topic/userOrder/%s/%s",
                          orders.getStoreId(), order.getOrderId());
                  messageTemplate.convertAndSend(topic, "주문 접수 중");
                  return ResponseEntity.ok("주문 접수 요청에 성공했습니다." + order.getOrderStatus());
                })
                .exceptionally(ex -> {
                  log.error("주문 처리 중 예외 발생", ex);
                  messageTemplate.convertAndSend(ERROR_TOPIC, "주문 접수 실패");
                  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                          .body("주문 처리 중 문제가 발생했습니다: " + ex.getMessage());
                });

        if (cart != null) {
          cart.setOrderId(null);
          cart.setTotalPrice(null);
          cart.setStoreId(null);
          cart.setDeliveryRequirement(null);
          cart.setStoreRequirement(null);
          if (cart.getCartItems() != null) {
            cart.getCartItems().clear();
          }
          usersCartRepository.save(cart);
        }
      }


      return responseBody;

    } catch (Exception e) {
      throw new RuntimeException("Failed to approve payment", e);
    }
  }

  /**
   * 결제 실패 처리
   */
  public void failPayment(String orderId, String paymentKey, String code, String message) {

    Payment payment = paymentRepository.findByOrderId(orderId).orElse(null);
    if (payment != null) {
      payment.setPaymentKey(paymentKey);
      payment.setStatus("FAIL");
      payment.setFailReason("Code: " + code + ", Msg: " + message);
    }

    deleteOrders(orderId);
  }


  public void deleteOrders(String paymentId) {
    Orders orders=ordersRepository.findByPaymentId(paymentId);
    ordersRepository.delete(orders);
  }
  // Base64 인코딩 메서드
  private String encodeToBase64(String value) {
    return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }



}
