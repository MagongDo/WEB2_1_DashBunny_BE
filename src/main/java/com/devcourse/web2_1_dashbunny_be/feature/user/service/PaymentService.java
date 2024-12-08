package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.config.TossPaymentConfig;
import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import com.devcourse.web2_1_dashbunny_be.domain.user.Payment;
import com.devcourse.web2_1_dashbunny_be.feature.order.repository.OrdersRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.PaymentRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

  private final WebClient webClient;
  private final TossPaymentConfig tossPaymentsConfig;
  private final PaymentRepository paymentRepository;
  private final RestTemplate restTemplate;
  private final UsersCartRepository usersCartRepository;
  private final OrdersRepository ordersRepository;

  /**
   * 결제 준비 요청
   */
  public PaymentResponseDto requestPayment(PaymentRequestDto requestDto)  {
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
     log.info(url);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + encodeToBase64(tossPaymentsConfig.getSecretKey() + ":"));
    headers.add("Content-Type", "application/json");

    HttpEntity<PaymentRequestDto> entity = new HttpEntity<>(requestDto, headers);
    ResponseEntity<PaymentResponseDto> response = restTemplate.exchange(
            url, HttpMethod.POST, entity, PaymentResponseDto.class
    );

    PaymentResponseDto responseBody = response.getBody();
    payment.setPaymentKey(responseBody.getPaymentKey());
    paymentRepository.save(payment);
    return responseBody;

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

    // HTTP 요청 엔티티 생성
    HttpEntity<PaymentApproveRequestDto> entity = new HttpEntity<>(approveRequest, headers);
    try {
      // REST API 호출
      ResponseEntity<PaymentApproveResponseDto> response = restTemplate.exchange(
              url, HttpMethod.POST, entity, PaymentApproveResponseDto.class
      );

      // 응답 본문 처리
      PaymentApproveResponseDto responseBody = response.getBody();

      if (responseBody == null) {
        log.error("Response body is null");
        throw new RuntimeException("PaymentApproveResponseDto is null");
      }

      log.info("Response received: {}", responseBody);

      // 결제 승인 후 처리
      Payment payment = paymentRepository.findByOrderId(responseBody.getOrderId())
              .orElseThrow(() -> new RuntimeException("Payment not found"));
      payment.setPaymentKey(responseBody.getPaymentKey());
      payment.setStatus(responseBody.getStatus());
      paymentRepository.save(payment);

      log.info("Payment status updated: {}", payment.getStatus());

      // 성공적인 결제의 경우 카트 비우기
      if ("DONE".equals(responseBody.getStatus())) {
        Cart cart = usersCartRepository.findByOrderId(approveRequest.getOrderId());
        if (cart != null) {
          cart.setOrderId(null);
          cart.setTotalPrice(null);
          cart.setStoreId(null);
          if (cart.getCartItems() != null) {
            cart.getCartItems().clear();
          }
          usersCartRepository.save(cart);
          log.info("Cart cleared for orderId: {}", approveRequest.getOrderId());
        } else {
          log.warn("No cart found for orderId: {}", approveRequest.getOrderId());
        }
      }


      return responseBody;

    } catch (Exception e) {
      log.error("Exception occurred during approvePayment: {}", e.getMessage(), e);
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
