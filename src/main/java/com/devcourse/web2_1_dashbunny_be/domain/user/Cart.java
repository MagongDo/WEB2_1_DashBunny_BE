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

//  @Column(name = "userCouponId")
//  private String userCouponId;

  @Column(name = "totalPrice")
  private Long totalPrice;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<CartItem> cartItems;

  @OneToOne
  @JoinColumn(name = "userCouponId") // UserCoupon과 명시적 연관 관계
  private UserCoupon userCoupon;


  @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Payment payment; // 결제와의 관계

}
