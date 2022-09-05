package com.nhnacademy.marketgg.server.dto.response.order;

import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.coupon.GivenCouponResponse;
import com.nhnacademy.marketgg.server.dto.response.deliveryaddress.DeliveryAddressResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 주문서 입력 폼 출력을 위해 취합한 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Builder
@Getter
public class OrderFormResponse {

    private final List<ProductToOrder> products; // 주문할 상품 목록

    private final Long memberId;
    private final String memberName;
    private final String memberPhone;
    private final String memberEmail;
    private final String memberGrade;

    // memo: 우선은 보유쿠폰목록, 보유포인트 담아보내고 나중에 비동기 처리하거나 하면 지워도 됨
    private final List<GivenCouponResponse> givenCouponList;
    private final Integer totalPoint; // 보유포인트

    private final List<DeliveryAddressResponse> deliveryAddressList; // 회원의 배송지 목록

    private final List<String> paymentType; // 결제수단 목록

    private final Long totalOrigin; // 원가

}
