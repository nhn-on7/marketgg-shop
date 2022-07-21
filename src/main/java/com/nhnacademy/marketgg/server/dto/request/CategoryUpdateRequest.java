package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class CategoryUpdateRequest {

    @NotBlank
    @Size(max = 6)
    private String categoryCode;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    private Integer sequence;

}
