package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryRetrieveResponse {

    private final String categoryCode;

    private final String categorizationName;

    private final String categoryName;

    private final Integer sequence;

}
