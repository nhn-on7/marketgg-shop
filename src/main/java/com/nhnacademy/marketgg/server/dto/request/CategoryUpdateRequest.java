package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryUpdateRequest {

    private String categoryCode;

    private String name;

    private Integer sequence;

}
