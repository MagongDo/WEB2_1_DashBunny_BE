package com.devcourse.web2_1_dashbunny_be.feature.user.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.Cart;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersCartRepository extends JpaRepository<Cart, Long> {
  Cart findByUser(User user);
  Cart findByOrderId(String orderId);
}
