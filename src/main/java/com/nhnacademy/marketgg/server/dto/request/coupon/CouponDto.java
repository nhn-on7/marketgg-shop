package com.nhnacademy.marketgg.server.dto.request.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "쿠폰 번호", description = "쿠폰의 고유 번호 입니다.", example = "5")
    private final Long id;

    @Schema(title = "쿠폰 이름", description = "등록할 쿠폰의 이름입니다.", example = "생일 쿠폰")
    @NotBlank(message = "쿠폰 이름이 유효하지 않습니다.")
    @Size(min = 3, max = 15, message = "쿠폰의 이름은 3자 이상, 15자 이하만 가능합니다.")
    private final String name;

    @Schema(title = "쿠폰 타입", description = "정률할인과 정액할인을 구분하는 타입 입니다.", example = "정률할인")
    @NotBlank
    private final String type;

    @Schema(title = "유효기간", description = "쿠폰의 유효한 기간을 일 수로 저장합니다.", example = "30")
    @NotNull
    @Positive
    private final Integer expiredDate;

    @Schema(title = "최소 주문 금액", description = "쿠폰을 사용할 수 있는 최소 주문 금액 입니다.", example = "5000")
    @NotNull
    @Positive(message = "최소 주문 금액은 0원이 될 수 없습니다.")
    private final Integer minimumMoney;

    @Schema(title = "할인량",
        description = "쿠폰의 할인량입니다. 정액할인이면 금액을 나타내고 정률할인이면 퍼센트를 나타냅니다.", example = "0.1")
    @NotNull
    @Positive(message = "할인량은 음수가 될 수 없습니다.")
    private final Double discountAmount;

}
