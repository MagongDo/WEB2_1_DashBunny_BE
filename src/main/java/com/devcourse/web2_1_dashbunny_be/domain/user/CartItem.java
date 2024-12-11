package com.devcourse.web2_1_dashbunny_be.domain.user;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cartItemId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cartId", nullable = false)
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menuId", nullable = false)
  private MenuManagement menuManagement;

  @Column(name = "quantity", nullable = false)
  private Long quantity;
}