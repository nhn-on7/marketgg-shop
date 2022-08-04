package com.nhnacademy.marketgg.server.dto.response.dib;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class DibRetrieveResponse {

    private final Long productNo;

    private final String productName;

    private final Long productPrice;

    private final LocalDateTime createdAt;

}
