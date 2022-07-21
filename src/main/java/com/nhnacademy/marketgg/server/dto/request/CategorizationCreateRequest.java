package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class CategorizationCreateRequest {

    @NotBlank
    @Size(max = 3)
    private String categorizationCode;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank

    private String alias;

}
