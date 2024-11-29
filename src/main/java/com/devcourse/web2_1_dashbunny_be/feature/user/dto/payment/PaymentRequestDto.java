package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
  @NotNull(message = "Cart ID는 필수입니다.")
  private Long cartId;

  @NotBlank(message = "Order Name은 필수입니다.")
  private String orderName;

  @NotNull(message = "Items는 필수입니다.")
  @Valid
  private List<PaymentItemDto> items;
}