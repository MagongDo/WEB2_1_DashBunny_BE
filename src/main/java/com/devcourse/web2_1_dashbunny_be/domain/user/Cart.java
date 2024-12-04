package com.devcourse.web2_1_dashbunny_be.domain.user;

import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private Long cartId;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "store_id")
  private String storeId;

  @Column(name = "user_coupon_id")
  private String userCouponId;

  @Column(name = "total_price")
  private Long totalPrice;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<CartItem> cartItems;

  @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Payment payment; // 결제와의 관계

}
