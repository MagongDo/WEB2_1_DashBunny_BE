package com.devcourse.web2_1_dashbunny_be.feature.user.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

}
