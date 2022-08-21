package com.nhnacademy.marketgg.server.dto.request.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UsedCouponDto {

    @Schema(title = "주문번호", description = "쿠폰을 사용할 주문 번호 입니다.", example = "1")
    @NotNull
    private Long orderId;

    @Schema(title = "쿠폰번호", description = "사용할 쿠폰 번호 입니다.", example = "1")
    @NotNull
    private Long couponId;

    @Schema(title = "회원번호", description = "쿠폰을 사용하는 회원 번호 입니다.", example = "1")
    @NotNull
    private Long memberId;

}
