package com.nhnacademy.marketgg.server.dto.response.order;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 요청에 대해 자체적으로 주문 데이터를 관리하고, 이후 결제 절차로 넘어갈 때 필요한 데이터를 전달하기 위한 클래스입니다.
 *
 * @author 김정민
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class OrderToPayment {

    @Schema(title = "주문번호", description = "마켓 GG 에서 관리하며, 앞에 GGORDER_ 로 구성된 주문번호입니다.", example = "GGORDER_628")
    @NotBlank
    @Size(min = 6, max = 64)
    private final String orderId;

    @Schema(title = "주문이름", description = "결제에 대한 주문 이름입니다.", example = "[프렙] 쉬림프 로제 리조또 외 3건")
    @NotBlank
    @Size(max = 100)
    private final String orderName;

    @Schema(title = "고객 이름", description = "주문 및 결제 요청을 한 회원(고객)의 이름입니다.", example = "강태풍")
    @NotBlank
    @Size(max = 100)
    private final String memberName;

    @Schema(title = "고객 이메일 주소", description = "주문 및 결제 요청을 한 회원(고객)의 이메일 주소입니다.",
            example = "strong.storm@gmail.com")
    @NotBlank
    @Size(max = 100)
    private final String memberEmail;

    @Schema(title = "총 결제금액", description = "적립금 및 쿠폰 내역이 반영된 최종 결제 금액입니다.", example = "75600")
    @NotNull
    private final Long totalAmount;

    @Schema(title = "쿠폰 아이디", description = "주문 내역에 대해 쿠폰 사용 여부를 확인하기 위한 아이디입니다.", example = "33")
    private final Long couponId;

    @Schema(title = "사용 적립금", description = "주문 내역에 대해 사용한 적립금입니다.", example = "10000")
    private final Integer usedPoint;

    @Schema(title = "예상 적립금", description = "주문 이후 생기는 예상 적립금입니다.", example = "1000")
    private final Integer expectedSavePoint;

}
