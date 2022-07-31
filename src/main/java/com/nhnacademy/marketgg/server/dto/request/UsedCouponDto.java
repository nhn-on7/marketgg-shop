package com.nhnacademy.marketgg.server.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
