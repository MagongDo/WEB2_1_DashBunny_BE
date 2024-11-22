package com.devcourse.web2_1_dashbunny_be.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;




}
