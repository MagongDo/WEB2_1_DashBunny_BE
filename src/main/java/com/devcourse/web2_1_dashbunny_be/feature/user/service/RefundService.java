package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.config.TossPaymentConfig;
import com.devcourse.web2_1_dashbunny_be.domain.user.Payment;
import com.devcourse.web2_1_dashbunny_be.domain.user.Refund;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.Refund.RefundRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.Refund.RefundResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.PaymentRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefundService {
  private final RefundRepository refundRepository;
  private final TossPaymentConfig tossPaymentsConfig;
  private final RestClient restClient;
  private final PaymentRepository paymentRepository;


  @Transactional
  public RefundResponseDto createdRefund(String paymentKey, RefundRequestDto refundRequestDto) {
    Payment payment = paymentRepository.findByPaymentKey(paymentKey);
    String url = tossPaymentsConfig.getApiBaseUrl() + "/payments/" + paymentKey + "/cancel";
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + encodeToBase64(tossPaymentsConfig.getSecretKey() + ":"));
    headers.add("Content-Type", "application/json");

    RefundResponseDto response = restClient.post()
            .uri(url)
            .body(refundRequestDto)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .retrieve()
            .body(RefundResponseDto.class);

    if(response.getStatus().equals("CANCELED")) {
      Refund refund = Refund.builder()
              .refundId(response.getRefundId())
              .status(response.getStatus())
              .amount(response.getAmount())
              .payment(payment.getId())
              .reason(response.getReason())
              .refundedAt(LocalDateTime.now()).build();
      refundRepository.save(refund);
      payment.setStatus("CANCELED");
      paymentRepository.save(payment);

    } else {
      handleRefundFailure(payment, response, refundRequestDto);
    }
    return response;
  }


  private void handleRefundFailure(Payment payment, RefundResponseDto response, RefundRequestDto refundRequestDto) {
    Refund refund = Refund.builder()
            .refundId(response.getRefundId() != null ? response.getRefundId() : "UNKNOWN")
            .status(response.getStatus())
            .amount(refundRequestDto.getCancelAmount())
            .payment(payment.getId())
            .reason(response.getReason())
            .refundedAt(LocalDateTime.now())
            .build();
    refundRepository.save(refund);
    payment.setStatus("REFUND_FAILED");
    paymentRepository.save(payment);
  }



  private String encodeToBase64(String value) {
    return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }
}
