package com.devcourse.web2_1_dashbunny_be.domain.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCoupon {

    @Id
    @UuidGenerator
    private String couponId;

    @Column(nullable = false)
    private String couponName;

    private Long discountPrice;

    private Long minOrderPrice;



    @Column(nullable = false)
    private String couponDescription; //쿠폰 상세 내용



}
