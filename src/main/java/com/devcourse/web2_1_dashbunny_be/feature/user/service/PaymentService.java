package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.config.TossPaymentConfig;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.domain.user.Payment;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.PaymentStatus;
import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.PaymentRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UsersCartRepository;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class PaymentService {
  private final PaymentRepository paymentRepository;
  private final UsersCartRepository cartRepository;
  private final TossPaymentConfig tossPaymentConfig;
  private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
  private final StoreManagementRepository storeManagementRepository;
  private final RestTemplate restTemplate;

    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {
    // 1. Cart 엔티티 조회
    Cart cart = cartRepository.findById(requestDto.getCartId())
            .orElseThrow(() -> new RuntimeException("Cart not found"));
    // 2. StoreManagement 엔티티 조회
    StoreManagement store = storeManagementRepository.findByStoreId(cart.getStoreId());
    // 3. Payment 엔티티 생성 및 저장
    Payment payment = new Payment();
    payment.setCart(cart);
    payment.setAmount(cart.getTotalPrice());
    payment.setStatus(PaymentStatus.READY);
    paymentRepository.save(payment);
    // 4. Toss Payments API 요청 생성
    String url = tossPaymentConfig.getApiBaseUrl() + "/payments";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Basic " + encodeToBase64(tossPaymentConfig.getSecretKey() + ":"));

    JsonObject body = new JsonObject();
    body.addProperty("method", "카드"); // 결제 수단 지정
    body.addProperty("orderId", payment.getId().toString());
    body.addProperty("orderName", requestDto.getOrderName());
    body.addProperty("amount", payment.getAmount());
    body.addProperty("successUrl", tossPaymentConfig.getSuccessUrl());
    body.addProperty("failUrl", tossPaymentConfig.getFailUrl());
    HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
      if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
        JsonObject responseBody = JsonParser.parseString(response.getBody()).getAsJsonObject();
        String paymentKey = responseBody.has("paymentKey") ? responseBody.get("paymentKey").getAsString() : null;
        // `checkout.url` 추출
        String paymentUrl = null;
        if (responseBody.has("checkout")) {
          JsonObject checkoutObject = responseBody.getAsJsonObject("checkout");
          if (checkoutObject.has("url")) {
            paymentUrl = checkoutObject.get("url").getAsString();
          }
        }
        if (paymentKey == null || paymentUrl == null) {
          logger.error("Missing paymentKey or checkout.url in the response");
          throw new RuntimeException("Invalid response from Toss Payments API");
        }
        // 5. Payment 엔티티에 paymentKey 설정
        payment.setPaymentId(paymentKey);
        paymentRepository.save(payment);
        // 6. PaymentResponseDto 생성
        PaymentResponseDto responseDto = PaymentResponseDto.builder()
            .paymentId(paymentKey)
            .orderName(requestDto.getOrderName())
            .amount(payment.getAmount())
            .status(payment.getStatus().name())
            .storeName(store.getStoreName())
            .successUrl(tossPaymentConfig.getSuccessUrl())
            .failUrl(tossPaymentConfig.getFailUrl())
            .clientKey(tossPaymentConfig.getClientKey())
            .redirectUrl(paymentUrl) // `checkout.url` 사용
            .build();

        return responseDto;
      } else {
        logger.error("Failed to create payment. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
        throw new RuntimeException("Failed to create payment");
      }
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("Toss Payments API error response status: {}", e.getStatusCode());
      logger.error("Toss Payments API error response body: {}", e.getResponseBodyAsString());
      throw new RuntimeException("Toss Payments API error: " + e.getMessage(), e);
    } catch (Exception e) {
      logger.error("Exception occurred while creating payment: ", e);
      throw new RuntimeException("Exception occurred while creating payment", e);
    }
  }


    // 결제 승인 메서드
    @Transactional
    public PaymentResponseDto approvePayment(String paymentKey, String orderId, Long amount) {
    // Payment 엔티티 조회
    Payment payment = paymentRepository.findById(Long.parseLong(orderId))
            .orElseThrow(() -> {
        logger.error("Payment with orderId {} not found.", orderId);
        return new RuntimeException("Payment not found");
    });
    if (payment.getStatus() == PaymentStatus.SUCCESS) {
      logger.info("Payment {} is already approved.", paymentKey);
      return PaymentResponseDto.builder()
                  .paymentId(paymentKey)
                  .orderName(payment.getCart().getCartId().toString())
                  .amount(payment.getAmount())
                  .status(payment.getStatus().name())
                  .build();
    }

    // 토스 결제 승인 API 호출
    String url = tossPaymentConfig.getApiBaseUrl() + "/payments/" + paymentKey + "/confirm";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String secretKey = tossPaymentConfig.getSecretKey();
    headers.set("Authorization", "Basic " + encodeToBase64(secretKey + ":"));

    JsonObject body = new JsonObject();
    body.addProperty("paymentKey", paymentKey);
    body.addProperty("orderId", orderId);
    body.addProperty("amount", amount);
    HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
      logger.info("Toss Payments API response: {}", response.getBody());

      if (response.getStatusCode() == HttpStatus.OK) {
        // 결제 성공 처리
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        // 결제 성공 시 장바구니 비우기
        Cart cart = payment.getCart();
        cart.getCartItems().clear();
        cart.setStoreId(null);
        cartRepository.save(cart);

        logger.info("Payment {} approved successfully.", paymentKey);

        PaymentResponseDto responseDto = PaymentResponseDto.builder()
                .paymentId(paymentKey)
                .orderName(payment.getCart().getCartId().toString())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .build();

        return responseDto;
      } else {
        logger.error("Failed to approve payment: {}", response.getBody());
        throw new RuntimeException("Failed to approve payment");
      }
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("Toss Payments API error response: {}", e.getResponseBodyAsString());
      throw new RuntimeException("Toss Payments API error: " + e.getMessage(), e);
    } catch (Exception e) {
      logger.error("Exception occurred while approving payment: ", e);
      throw new RuntimeException("Exception occurred while approving payment", e);
    }
  }

    // 웹훅 처리 메서드
    @Transactional
    public void handleWebhook(String paymentKey, String status) {
    Payment payment = paymentRepository.findByPaymentId(paymentKey)
            .orElseThrow(() -> {
      logger.warn("Payment with paymentKey {} not found.", paymentKey);
      return new RuntimeException("Payment not found for webhook");
    });

    if ("DONE".equalsIgnoreCase(status)) {
      payment.setStatus(PaymentStatus.SUCCESS);
      // 결제 성공 시 장바구니 비우기
      Cart cart = payment.getCart();
      cart.getCartItems().clear();
      cart.setStoreId(null);
      cartRepository.save(cart);
      logger.info("Payment {} updated to status {} and cart {} has been cleared", paymentKey, status, cart.getCartId());
    } else {
      payment.setStatus(PaymentStatus.FAIL);
      logger.info("Payment {} updated to status {}", paymentKey, status);
    }
    payment.setUpdatedAt(LocalDateTime.now());
    paymentRepository.save(payment);
  }

    // 결제 상세 정보 조회 메서드
    public Optional<Payment> findByPaymentId(String paymentId) {
      return paymentRepository.findByPaymentId(paymentId);
  }

    // Base64 인코딩 메서드
    private String encodeToBase64(String value) {
      return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
}
