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

    @OneToOne
    @JoinColumn(name = "storeId")
    private StoreManagement storeId;

    @Column(name = "userCouponId")
    private String userCouponId;

    @Column(name = "totalPrice")
    private Long totalPrice;

    @OneToMany(mappedBy = "cartItemId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

}
