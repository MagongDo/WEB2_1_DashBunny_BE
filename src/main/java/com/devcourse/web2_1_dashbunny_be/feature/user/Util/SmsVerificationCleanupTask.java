package com.devcourse.web2_1_dashbunny_be.feature.user.Util;

import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SmsVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDateTime;

@Log4j2
@Component
@RequiredArgsConstructor
public class SmsVerificationCleanupTask {

    private final SmsVerificationRepository smsVerificationRepository;

    // 매 시간마다 실행 (크론 표현식: 매일 4시)
    @Scheduled(cron = "0 0 4 * * *")
    public void cleanUpExpiredVerifications() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(5);
        smsVerificationRepository.deleteByCreatedAtBefore(expiryTime);
        log.info("만료된 SMS 인증번호 삭제");
    }
}
