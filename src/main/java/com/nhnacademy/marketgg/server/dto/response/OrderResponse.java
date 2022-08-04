package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class OrderResponse {

    private final Long id;

    private final Long memberId;

    private final Long totalAmount;

    private final String orderStatus;

    private final LocalDateTime createdAt;

}
