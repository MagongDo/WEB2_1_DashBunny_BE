package com.devcourse.web2_1_dashbunny_be.feature.order.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 주문 내역 repository.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

   Optional<Orders> findByStoreId(String storeId);
}
