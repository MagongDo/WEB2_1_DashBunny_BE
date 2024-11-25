package com.devcourse.web2_1_dashbunny_be.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class) // Date를 등록, 수정 일시 자동 반영 중요!!
@NoArgsConstructor
@AllArgsConstructor
public class SmsVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 11)
    @Pattern(regexp = "^010\\d{8}$", message = "유효한 전화번호 형식이 아닙니다. 예: 01012345678")
    private String phoneNumber;

    private String verificationCode;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isUsed = false; // 인증번호 사용 여부

}
