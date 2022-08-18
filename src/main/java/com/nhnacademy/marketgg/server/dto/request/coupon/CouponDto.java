package com.nhnacademy.marketgg.server.dto.request.coupon;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * 쿠폰의 요청과 응답의 DTO 입니다.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class CouponDto {

    private final Long id;

    @NotBlank(message = "쿠폰 이름이 유효하지 않습니다.")
    @Size(min = 3, max = 15, message = "쿠폰의 이름은 3자 이상, 15자 이하만 가능합니다.")
    private final String name;

    @NotBlank
    private final String type;

    @NotNull
    @Positive
    private final Integer expiredDate;

    @NotNull
    @Positive(message = "최소 주문 금액은 0원이 될 수 없습니다.")
    private final Integer minimumMoney;

    @NotNull
    @Positive(message = "할인량은 음수가 될 수 없습니다.")
    private final Double discountAmount;

}
