package com.nhnacademy.marketgg.server.dto.response.temp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class CommonResponse {

    protected boolean success;

    protected CommonResponse(boolean success) {
        this.success = success;
    }

}
