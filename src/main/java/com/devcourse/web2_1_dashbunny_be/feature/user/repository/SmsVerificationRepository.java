package com.devcourse.web2_1_dashbunny_be.feature.user.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.SmsVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {

    void deleteByPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query("UPDATE SmsVerification v SET v.isUsed = true WHERE v.phoneNumber = :phoneNumber AND v.isUsed = false")
    void updateIsUsedByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    Optional<SmsVerification> findTopByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);

    @Transactional
    @Modifying
    @Query("DELETE FROM SmsVerification v WHERE v.createdAt < :expiryTime")
    void deleteByCreatedAtBefore(@Param("expiryTime") LocalDateTime expiryTime);

}
