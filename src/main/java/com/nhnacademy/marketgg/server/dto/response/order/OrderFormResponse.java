package com.nhnacademy.marketgg.server.dto.response.order;

import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
public class OrderFormResponse {

    private final List<ProductToOrder> products;
    // private final Long productId;
    // private final String productName;
    // private final Long productPrice;
    // private final Integer productAmount;
    private final String memberName;
    private final String memberEmail;
    private final boolean haveGgpass; // GG패스 보유여부
    // memo#1: 우선은 보유쿠폰목록, 보유포인트 담아보내고 나중에 비동기 처리하거나 하면 지우기
    private final List<OrderGivenCoupon> givenCouponList;
    // private final Long couponId;
    // private final String couponName;
    // private final LocalDateTime couponExpiredAt;
    // private final Integer couponMinimumMoney;
    // private final Double couponDiscountAmount; // 할인량
    private final Integer totalPoint; // 보유포인트
    // memo#1: 여기까지
    private final List<String> orderType; // 결제수단 종류
    private final Long totalOrigin; // 원가
    private final Integer couponDiscount; // 쿠폰적용할인금액
    private final Integer usedPoint; // 포인트사용금액
    private final Long totalAmount; // 최종결제금액
    private final Integer savePoint; // 적립예상금액

}
