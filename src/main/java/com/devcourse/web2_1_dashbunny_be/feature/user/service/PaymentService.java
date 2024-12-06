package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.config.TossPaymentConfig;
import com.devcourse.web2_1_dashbunny_be.domain.user.Payment;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentApproveResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment.PaymentRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final RestTemplate restTemplate;
  private final TossPaymentConfig tossPaymentsConfig;
  private final PaymentRepository paymentRepository;

  /**
   * 결제 준비 요청
   */
  public PaymentResponseDto requestPayment(PaymentRequestDto requestDto) {
    // DB에 결제 요청 정보 저장 (status: READY)
    Payment payment = Payment.builder()
            .orderId(requestDto.getOrderId())
            .orderName(requestDto.getOrderName())
            .amount(requestDto.getAmount())
            .customerName(requestDto.getCustomerName())
            .status("READY")
            .build();
    paymentRepository.save(payment);

    String url = tossPaymentsConfig.getApiBaseUrl() + "/payments";
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + encodeToBase64(tossPaymentsConfig.getSecretKey() + ":"));
    headers.add("Content-Type", "application/json");

    HttpEntity<PaymentRequestDto> entity = new HttpEntity<>(requestDto, headers);
    ResponseEntity<PaymentResponseDto> response = restTemplate.exchange(
            url, HttpMethod.POST, entity, PaymentResponseDto.class
    );

    PaymentResponseDto responseBody = response.getBody();
    return responseBody;
  }

  /**
   * 결제 승인 요청
   */
  public PaymentApproveResponseDto approvePayment(PaymentApproveRequestDto approveRequest) {
    String url = tossPaymentsConfig.getApiBaseUrl() + "/payments/confirm";

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + encodeToBase64(tossPaymentsConfig.getSecretKey() + ":"));
    headers.add("Content-Type", "application/json");

    HttpEntity<PaymentApproveRequestDto> entity = new HttpEntity<>(approveRequest, headers);
    ResponseEntity<PaymentApproveResponseDto> response = restTemplate.exchange(
            url, HttpMethod.POST, entity, PaymentApproveResponseDto.class
    );

    PaymentApproveResponseDto res = response.getBody();
    // DB 업데이트
    Payment payment = paymentRepository.findByOrderId(res.getOrderId()).orElseThrow(() -> new RuntimeException("Payment not found"));
    payment.setPaymentKey(res.getPaymentKey());
    payment.setStatus(res.getStatus()); // "DONE" 등
    paymentRepository.save(payment);

    return res;
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
      paymentRepository.save(payment);
    }
  }

  // Base64 인코딩 메서드
  private String encodeToBase64(String value) {
    return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }
}