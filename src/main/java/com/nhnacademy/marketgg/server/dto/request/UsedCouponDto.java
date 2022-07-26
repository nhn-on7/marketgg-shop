package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UsedCouponDto {

    private Long orderId;

    private Long couponId;

    private Long memberId;

}
