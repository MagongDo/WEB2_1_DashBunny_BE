package com.devcourse.web2_1_dashbunny_be.feature.user.controller;


import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
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

    /**
     * 결제 실패 시 failUrl로 리다이렉트됨
     * 토스페이먼츠에서 GET으로 orderId, paymentKey, code, message를 쿼리 파라미터로 전달
     */
    @GetMapping("/fail")
    public String paymentFail(
            @RequestParam("orderId") String orderId,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("code") String code,
            @RequestParam("message") String message) {

        // 실패 처리 로직
        paymentService.failPayment(orderId, paymentKey, code, message);

        return "결제가 실패하였습니다. 주문번호: " + orderId + ", 실패 코드: " + code + ", 사유: " + message;
    }
}