package com.devcourse.web2_1_dashbunny_be.feature.user.controller;


import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    /**
     * 결제 요청 API
     * 프론트에서 orderId, orderName, amount, customerName, successUrl, failUrl를 포함하여 요청
     */
    @PostMapping("/request")
    public PaymentResponseDto requestPayment(@RequestBody PaymentRequestDto requestDto) {
        return paymentService.requestPayment(requestDto);
    }

    /**
   * 결제 완료 후 승인 처리 API
   * successUrl로 리다이렉트된 후 클라이언트나 프론트엔드에서 paymentKey, orderId, amount를 받아 이 API 호출
   */
  @PostMapping("/approve")
  public PaymentApproveResponseDto approvePayment(@RequestBody PaymentApproveRequestDto approveRequest) {
      return paymentService.approvePayment(approveRequest);
  }

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

            // 결제 성공 시 /carts로 리다이렉트 (쿼리 파라미터에 상태 포함)
            String redirectUrl = "http://localhost:3000/cart?status=success&orderId=" + approveResponse.getOrderId();
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            // 승인 실패 시 /carts로 리다이렉트
            String redirectUrl = "http://localhost:3000/cart?status=failure&reason=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
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

        // 실패 시 /carts로 리다이렉트 (쿼리 파라미터에 실패 정보 포함)
        String redirectUrl = "http://localhost:3000/cart?status=failure&reason=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect(redirectUrl);
    }
}