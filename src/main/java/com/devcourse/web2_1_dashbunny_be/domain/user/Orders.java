package com.devcourse.web2_1_dashbunny_be.domain.user;

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
  private Long orderId;

  @JoinColumn()
  private Long userId;

  @JoinColumn()
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
}

//