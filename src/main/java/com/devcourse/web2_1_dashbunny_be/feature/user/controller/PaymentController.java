package com.devcourse.web2_1_dashbunny_be.feature.user.controller;


import com.devcourse.web2_1_dashbunny_be.feature.user.dto.Refund.RefundRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.Refund.RefundResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.PaymentService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.RefundService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

  private final PaymentService paymentService;
  private final RefundService refundService;


  @GetMapping("/success")
  public void paymentSuccess(
          @RequestParam("orderId") String orderId,
          @RequestParam("paymentKey") String paymentKey,
          @RequestParam("amount") Long amount,
          HttpServletResponse response
  ) throws IOException {
    try {
      // 결제 승인 처리
      PaymentApproveRequestDto approveRequest = PaymentApproveRequestDto.builder()
               .orderId(orderId)
               .paymentKey(paymentKey)
               .amount(amount)
               .build();
      PaymentApproveResponseDto approveResponse = paymentService.approvePayment(approveRequest);

      String redirectUrl = "http://localhost:3000/payment-result?status=success&orderId=" + approveResponse.getOrderId();
      response.sendRedirect(redirectUrl);
    } catch (Exception e) {

      String redirectUrl = "http://localhost:3000/payment-result?status=failure&reason=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
      paymentService.deleteOrders(orderId);
      response.sendRedirect(redirectUrl);
    }
  }
  /**
   * 결제 실패 시 failUrl로 리다이렉트됨
   * 토스페이먼츠에서 GET으로 orderId, paymentKey, code, message를 쿼리 파라미터로 전달
   */
  @GetMapping("/fail")
  public void paymentFail(
          @RequestParam("orderId") String orderId,
          @RequestParam("paymentKey") String paymentKey,
          @RequestParam("code") String code,
          @RequestParam("message") String message,
          HttpServletResponse response
  ) throws IOException {
    // 결제 실패 처리
    paymentService.failPayment(orderId, paymentKey, code, message);
    paymentService.deleteOrders(orderId);

    String redirectUrl = "http://localhost:3000/payment-result?status=failure&reason=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    response.sendRedirect(redirectUrl);
  }

  @PostMapping("/{paymentKey}")

  public void refund(
          @PathVariable String paymentKey,
          @RequestBody RefundRequestDto refundRequest,
          HttpServletResponse response
  ) throws IOException {
    try {
      RefundResponseDto refundResponse = refundService.createRefund(paymentKey, refundRequest);
      String redirectUrl = "http://localhost:3000/payment-result?status=success";
      response.sendRedirect(redirectUrl);

    } catch (Exception e) {
      // 에러 응답 반환
      String redirectUrl = "http://localhost:3000/payment-result?status=failure&reason=" + URLEncoder.encode("결제 취소에 실패했습니다.", StandardCharsets.UTF_8);
      response.sendRedirect(redirectUrl);
    }
  }
}