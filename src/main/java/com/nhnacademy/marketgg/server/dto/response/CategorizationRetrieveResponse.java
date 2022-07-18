package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategorizationRetrieveResponse {

    private final String categorizationCode;

    private final String name;

}
