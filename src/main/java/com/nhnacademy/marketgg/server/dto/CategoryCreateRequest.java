package com.nhnacademy.marketgg.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryCreateRequest {

    private String categoryCode;

    private String categorizationCode;

    private String name;

    private Integer sequence;

}
