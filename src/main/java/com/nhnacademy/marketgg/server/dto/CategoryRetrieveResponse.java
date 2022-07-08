package com.nhnacademy.marketgg.server.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryRetrieveResponse {

    private String categorizationCode;

    private String categoryCode;

    private String categorizationName;

    private String categoryName;

}
