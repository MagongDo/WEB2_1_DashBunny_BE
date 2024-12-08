package com.devcourse.web2_1_dashbunny_be.feature.admin.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequestMessage {
    private Long couponId;
    private Long userId;
}
