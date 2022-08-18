package com.nhnacademy.marketgg.server.dto.request.order;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문을 생성하기 위한 요청 정보를 담고 있는 DTO 클래스입니다.
 *
 * @version 1.0.0
 * @author 김정민
 */
@NoArgsConstructor
@Getter
public class OrderCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotNull
    private Long couponId;

    @NotNull
    private Long deliveryAddressId;

    // 장바구니에서 받아올 주문할 상품 목록
    @NotNull
    private List<ProductToOrder> products;

    @NotNull
    private Integer usedPoint;

    @NotNull
    private Long totalOrigin; // 원가

    @NotNull
    private Long totalAmount; // 최종 결제 금액

    @NotBlank
    private String paymentType;

    @NotNull
    private Integer expectedSavePoint;

}
