package com.devcourse.web2_1_dashbunny_be.domain.user;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.OrderStatus;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.controller.dto.OrderItemDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 주문내역 entity.
 * 아직 연관관계 설정이 안됨
 */
@Getter
@Setter
@Entity
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long orderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private String storeId;

    /*  private String storeName;*/
    //스토어 아이디를 해두는데 스토어 이름이 동시에 들어가는 조인을 해서 가져오는 방법은 어떨까요?

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems;

  private String couponDetails;

  private String deliveryAddress;

  private String storeNote;

  private String riderNote;

  private LocalDateTime orderDate;

  private LocalDateTime createdAt;

  private Long deliveryPrice;

  @Column(nullable = false)
  private Long totalPrice; // 총 금액 (단가 * 수량)

  private int totalMenuCount;

  private OrderStatus orderStatus = OrderStatus.PENDING;

  private int preparationTime;
}

//