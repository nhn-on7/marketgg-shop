package com.nhnacademy.marketgg.server.dto.response.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ErrorEntity extends CommonResponse {

    private String message;

}
