package com.nhnacademy.marketgg.server.dto.response.order;

import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
public class OrderFormResponse {

    private final List<ProductToOrder> products; // 주문할 상품 목록

    private final Long memberId;
    private final String memberName;
    private final String memberEmail;
    private final String memberGrade;
    private final boolean haveGgpass; // GG패스 보유여부 - 배송비 무료 조건

    // memo#1: 우선은 보유쿠폰목록, 보유포인트 담아보내고 나중에 비동기 처리하거나 하면 지워도 됌
    private final List<OrderGivenCoupon> givenCouponList;
    private final Integer totalPoint; // 보유포인트

    private final List<DeliveryAddressResponse> deliveryAddressList; // 회원의 배송지 목록

    private final List<String> paymentType; // 결제수단 목록

    private final Long totalOrigin; // 원가

}
