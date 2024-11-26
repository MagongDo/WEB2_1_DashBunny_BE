package com.devcourse.web2_1_dashbunny_be.feature.user.Util;

import com.devcourse.web2_1_dashbunny_be.domain.user.SmsVerification;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SmsVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int CODE_LENGTH = 6;
    private final SmsVerificationRepository smsVerificationRepository;

    /**
     * 6자리 랜덤 인증 코드를 생성 후 DB에 저장합니다.
     * @return 6자리 숫자로 이루어진 인증 코드
     */
    public String createCode(String phoneNumber) {

        // 기존 인증번호 비활성화
        smsVerificationRepository.updateIsUsedByPhone(phoneNumber);

        int code = secureRandom.nextInt(1_000_000); // 0부터 999999까지의 숫자 생성
        String verificationCode = String.format("%06d", code);

        SmsVerification verification = SmsVerification.builder()
                .phone(phoneNumber)
                .verificationCode(verificationCode)
                .build();

        smsVerificationRepository.save(verification);
        return verificationCode; // 6자리 숫자로 포맷 (앞에 0이 필요한 경우 포함)
    }
}
