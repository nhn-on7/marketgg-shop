package com.nhnacademy.marketgg.server.dto.response.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderToPayment {

    private final String orderId;

    private final String orderName;

    private final String memberName;

    private final String memberEmail;

    private final Long totalAmount;

}
