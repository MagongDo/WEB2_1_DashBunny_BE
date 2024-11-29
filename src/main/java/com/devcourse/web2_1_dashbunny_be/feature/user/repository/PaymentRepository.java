package com.devcourse.web2_1_dashbunny_be.feature.user.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(String paymentId);
}
