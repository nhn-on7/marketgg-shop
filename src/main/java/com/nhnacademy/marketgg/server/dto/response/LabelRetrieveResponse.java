package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LabelRetrieveResponse {

    private final Long labelNo;

    private final String name;

}
