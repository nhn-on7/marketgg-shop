package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryRetrieveResponse {

    private String categoryCode;

    private String categorizationName;

    private String categoryName;

    private Integer sequence;

}
