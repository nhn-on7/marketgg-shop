package com.nhnacademy.marketgg.server.dto.response.dib;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DibRetrieveResponse {

    private final Long productNo;

    private final String thumbnail;

    private final String productName;

    private final Long productPrice;

}
