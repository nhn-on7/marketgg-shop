package com.nhnacademy.marketgg.server.dto.response.order;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderResponse {

    private final Long id;

    private final Long memberId;

    private final Long totalAmount;

    private final String orderStatus;

    private final LocalDateTime createdAt;

}
