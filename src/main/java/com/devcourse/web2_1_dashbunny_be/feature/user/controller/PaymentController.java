package com.devcourse.web2_1_dashbunny_be.feature.user.controller;

import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.PaymentService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;
  private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

  // 결제 생성 엔드포인트
  @PostMapping("/create")
  public ResponseEntity<PaymentResponseDto> createPayment(@Valid @RequestBody PaymentRequestDto requestDto) {
    PaymentResponseDto responseDto = paymentService.createPayment(requestDto);
    return ResponseEntity.ok(responseDto);
  }

  // 결제 승인 엔드포인트
  @PostMapping("/confirm")
  public ResponseEntity<PaymentResponseDto> confirmPayment(@RequestParam String paymentKey,
                                                           @RequestParam String orderId,
                                                           @RequestParam Long amount) {
    PaymentResponseDto responseDto = paymentService.approvePayment(paymentKey, orderId, amount);
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/webhook")
  public ResponseEntity<Void> handleWebhook(@RequestBody String payload) {
    try {
      logger.info("Received webhook payload: {}", payload);

      // JSON 파싱
      JsonObject jsonObject = JsonParser.parseString(payload).getAsJsonObject();
      String paymentKey = jsonObject.get("paymentKey").getAsString();
      String status = jsonObject.get("status").getAsString();

      paymentService.handleWebhook(paymentKey, status);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      logger.error("Error processing webhook payload", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
  @GetMapping("/payment/success")
  public ResponseEntity<String> handleSuccess(
        @RequestParam String paymentKey,
        @RequestParam String orderId,
        @RequestParam Long amount) {
    try {
      paymentService.approvePayment(paymentKey, orderId, amount);
      return ResponseEntity.ok("Payment approved successfully.");
    } catch (Exception e) {
      logger.error("Error approving payment", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment approval failed.");
    }
  }
}
