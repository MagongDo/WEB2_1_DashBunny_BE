package com.devcourse.web2_1_dashbunny_be.domain.user;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 걀제가 완료된 메뉴 정보가 담긴 entity.
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderItemId; // 주문 항목 ID

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Orders order; // 해당 주문

  @ManyToOne
  @JoinColumn(name = "menu_id", nullable = false)
  private MenuManagement menu; // 메뉴 정보

  @Column(nullable = false)
  private int quantity; // 수량

  @Column(nullable = true)
  private Long totalPrice; // 총 합산 비용

}
