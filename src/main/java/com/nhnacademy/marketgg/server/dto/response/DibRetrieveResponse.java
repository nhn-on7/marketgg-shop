package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class DibRetrieveResponse {

    private final String productName;

    private final Long productPrice;

    private final String memo;

    private final LocalDateTime createdAt;

}
