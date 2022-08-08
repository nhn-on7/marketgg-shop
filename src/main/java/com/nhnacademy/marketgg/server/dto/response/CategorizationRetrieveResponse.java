package com.nhnacademy.marketgg.server.dto.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategorizationRetrieveResponse {

    @NotBlank
    @Size(max = 3)
    private final String categorizationCode;

    @NotBlank
    @Size(max = 20)
    private final String name;

}
