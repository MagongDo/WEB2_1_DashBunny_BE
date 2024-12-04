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
  @Column(name = "cartId")
  private Long cartId;

  @OneToOne
  @JoinColumn(name = "userId", nullable = false)
  private User user;

  @Column(name="storeId")
  private String storeId;

  @Column(name = "userCouponId", nullable = true) // 쿠폰 ID
  private String userCouponId; // UserCoupon의 ID를 식별자 참조 이유: 객체 참조하면 너무 복집해질거 같아서

  @Column(name = "totalPrice")
  private Long totalPrice;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<CartItem> cartItems;





  @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Payment payment; // 결제와의 관계

}
