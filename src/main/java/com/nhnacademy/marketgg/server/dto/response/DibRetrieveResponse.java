package com.nhnacademy.marketgg.server.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DibRetrieveResponse {

    private final Long productNo;

    private final String productName;

    private final Long productPrice;

    private final LocalDateTime createdAt;

}
