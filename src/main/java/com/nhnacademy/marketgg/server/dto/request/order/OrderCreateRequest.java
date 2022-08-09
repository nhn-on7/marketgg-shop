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
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderCreateRequest {

    @NotNull
    private Long memberId;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotNull
    private Long couponId;

    @NotNull
    private Long deliveryAddressId;

    // @NotNull
    // private Integer zipCode;
    //
    // @NotNull
    // private String address;
    //
    // @NotNull
    // private String detailAddress;

    // 장바구니에서 받아올 주문할 상품 목록
    @NotNull
    private List<ProductToOrder> products;

    @NotNull
    private Integer usedPoint;

    @NotNull
    private Long totalAmount;

    @NotBlank
    private String paymentType;

    @NotNull
    private Integer expectedSavePoint;

}
