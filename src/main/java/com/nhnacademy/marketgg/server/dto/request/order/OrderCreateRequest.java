package com.nhnacademy.marketgg.server.dto.request.order;

import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문을 생성하기 위한 요청 정보를 담고 있는 DTO 클래스입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderCreateRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long couponId;

    @NotNull
    private Long deliveryAddressId;

    // 상품번호, 수량
    @NotNull
    private Map<Long, Long> productMap;

    @NotNull
    private Integer usedPoint;

    @NotNull
    private Long totalAmount;

    // 결제 방식
    @NotBlank
    private String orderType;

    // 바로구매 vs 장바구니
    @NotNull
    private boolean isDirectBuy;

}
