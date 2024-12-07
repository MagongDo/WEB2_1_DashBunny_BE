package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponseDto {
  @JsonProperty("paymentKey")
  private String paymentKey;

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("totalAmount")
  private Long amount; // totalAmount을 amount로 매핑

  // Nested class to map 'checkout' object
  @JsonProperty("checkout")
  private Checkout checkout;

  public String getCheckoutUrl() {
      return (checkout != null) ? checkout.getUrl() : null;
  }

  @Data
  public static class Checkout {
      @JsonProperty("url")
        private String url;
  }
}
