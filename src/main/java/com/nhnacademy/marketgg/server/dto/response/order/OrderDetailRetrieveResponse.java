package com.nhnacademy.marketgg.server.dto.response.order;

import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.coupon.UsedCouponResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 상세 조회시 필요한 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class OrderDetailRetrieveResponse {

    private final Long id;

    private final Long memberId;

    private final String orderName;

    private final Long totalAmount;

    private final String orderStatus;

    private UsedCouponResponse usedCoupon;

    private final Integer usedPoint;

    private final String trackingNo;

    private final Integer zipCode;

    private final String address;

    private final String detailAddress;

    private final LocalDateTime createdAt;

    private List<ProductToOrder> orderProductList;

    public void addOrderDetail(List<ProductToOrder> orderProductList, UsedCouponResponse usedCoupon) {
        this.orderProductList = orderProductList;
        this.usedCoupon = usedCoupon;
    }

}
