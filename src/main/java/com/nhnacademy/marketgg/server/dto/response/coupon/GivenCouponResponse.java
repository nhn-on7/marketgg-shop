package com.nhnacademy.marketgg.server.dto.response.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class GivenCouponResponse {

    @Schema(title = "쿠폰 번호", description = "지급된 쿠폰의 고유 번호 입니다.", example = "1")
    private final Long couponId;

    @Schema(title = "회원 번호", description = "쿠폰의 지급된 회원 고유 번호 입니다.", example = "1")
    private final Long memberId;

    @Schema(title = "쿠폰 이름", description = "지급된 쿠폰의 이름 입니다.", example = "신규 가입 쿠폰")
    private final String name;

    @Schema(title = "쿠폰 타입", description = "정률할인과 정액할인을 구분하는 타입 입니다.", example = "정률할인")
    private final String type;

    @Schema(title = "최소 주문 금액", description = "쿠폰을 사용할 수 있는 최소 주문 금액 입니다.", example = "5000")
    private final Integer minimumMoney;

    @Schema(title = "할인량",
        description = "쿠폰의 할인량입니다. 정액할인이면 금액을 나타내고 정률할인이면 퍼센트를 나타냅니다.", example = "0.1")
    private final Double discountAmount;

    @Schema(title = "만료일자", description = "쿠폰의 만료일자 입니다.", example = "2022-08-20 20:00:00")
    private final LocalDateTime expiredDate;

    @Schema(title = "쿠폰 상태", description = "쿠폰의 상태는 기간만료, 사용완료, 사용가능 이 있습니다. ", example = "기간만료")
    private final String status;

}
