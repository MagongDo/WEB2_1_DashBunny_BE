package com.devcourse.web2_1_dashbunny_be.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {

  private String phone;
  private String verificationCode;
  private String newPassword;

}
