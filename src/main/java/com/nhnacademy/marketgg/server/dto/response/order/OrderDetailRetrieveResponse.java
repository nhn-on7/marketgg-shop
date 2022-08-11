package com.nhnacademy.marketgg.server.dto.response.order;

import java.time.LocalDateTime;
import java.util.List;

import com.nhnacademy.marketgg.server.dto.response.orderdeliveryaddress.OrderDeliveryAddressResponse;
import com.nhnacademy.marketgg.server.dto.response.orderproduct.OrderProductResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderDetailRetrieveResponse {

    private final Long id;

    private final Long memberId;

    private final Long totalAmount;

    private final String orderStatus;

    private final Integer usedPoint;

    private final Integer trackingNo;

    private final LocalDateTime createdAt;

    private OrderDeliveryAddressResponse orderDeliveryAddress;

    private List<OrderProductResponse> orderProductList;

    public void addOrderDetail(OrderDeliveryAddressResponse orderDeliveryAddress,
                               List<OrderProductResponse> orderProductList) {

        this.orderDeliveryAddress = orderDeliveryAddress;
        this.orderProductList = orderProductList;
    }

}
