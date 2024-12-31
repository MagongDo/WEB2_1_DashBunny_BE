package com.devcourse.web2_1_dashbunny_be.feature.user.controller;


import com.devcourse.web2_1_dashbunny_be.feature.user.dto.Refund.RefundRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.PaymentService;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.RefundService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
          @RequestParam("amount") Long amount

  ) {
    try {
      // 결제 승인 처리
      PaymentApproveRequestDto approveRequest = PaymentApproveRequestDto.builder()
               .orderId(orderId)
               .paymentKey(paymentKey)
               .amount(amount)
               .build();

      // 결제 성공 시 /carts로 리다이렉트 (쿼리 파라미터에 상태 포함)
      String redirectUrl = "http://localhost:3000/payment-result?status=success&orderId=" + approveRequest.getOrderId();
    } catch (Exception e) {
      // 승인 실패 시 /carts로 리다이렉트
      String redirectUrl = "http://localhost:3000/payment-result?status=failure&reason=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
    }
  }
  /**
   * 결제 실패 시 failUrl로 리다이렉트됨
   * 토스페이먼츠에서 GET으로 orderId, paymentKey, code, message를 쿼리 파라미터로 전달
   */
  @PostMapping("/fail")
  public void paymentFail(
          @RequestParam("orderId") String orderId,
          @RequestParam("paymentKey") String paymentKey,
          @RequestParam("code") String code,
          @RequestParam("message") String message,
          HttpServletResponse response
  ) throws IOException {
    // 결제 실패 처리
    paymentService.failPayment(orderId, paymentKey, code, message);
    // 실패 시 /carts로 리다이렉트 (쿼리 파라미터에 실패 정보 포함)
    String redirectUrl = "http://localhost:3000/payment-result?status=failure&reason=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    response.sendRedirect(redirectUrl);
  }

  @PostMapping("/{paymentKey}")
  public void refundPayment(
          @PathVariable String paymentKey,
          @RequestBody RefundRequestDto refundRequest,
          HttpServletResponse response
  ) throws IOException {
    try {
      refundService.createdRefund(paymentKey, refundRequest);
      String redirectUrl = "http://localhost:3000/payment-result?status=success";
      response.sendRedirect(redirectUrl);

    } catch (Exception e) {
      // 에러 응답 반환
      String redirectUrl = "http://localhost:3000/payment-result?status=failure&reason=" + URLEncoder.encode("결제 취소에 실패했습니다.", StandardCharsets.UTF_8);
      response.sendRedirect(redirectUrl);
    }
  }
}