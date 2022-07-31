package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class UsedCouponDto {

    @NotNull
    private Long orderId;

    @NotNull
    private Long couponId;

    @NotNull
    private Long memberId;

}
