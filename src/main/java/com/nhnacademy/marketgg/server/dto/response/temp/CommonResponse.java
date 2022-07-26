package com.nhnacademy.marketgg.server.dto.response.temp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class CommonResponse {

    protected final boolean success;
}
