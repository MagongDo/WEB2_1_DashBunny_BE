package com.devcourse.web2_1_dashbunny_be.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {

    private String phone;
    private String verificationCode;
    private String newPassword;

}
