package com.devcourse.web2_1_dashbunny_be.feature.user.dto.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PaymentItemDto {
  @NotBlank(message = "Product ID는 필수입니다.")
  private String productId;

  @NotNull(message = "Quantity는 필수입니다.")
  @Min(value = 1, message = "Quantity는 최소 1 이상이어야 합니다.")
  private Integer quantity;

  @NotNull(message = "Price는 필수입니다.")
  @Min(value = 1, message = "Price는 최소 1 이상이어야 합니다.")
  private Long price;
}
