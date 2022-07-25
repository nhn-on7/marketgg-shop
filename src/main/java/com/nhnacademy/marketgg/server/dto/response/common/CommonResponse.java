package com.nhnacademy.marketgg.server.dto.response.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class CommonResponse {

    protected boolean success;

    protected CommonResponse(boolean success) {
        this.success = success;
    }

}
