package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderCreateRequest {

    private Long totalAmount;

    private String orderStatus;

    private Integer usedPoint;

}
