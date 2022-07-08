package com.nhnacademy.marketgg.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryUpdateRequest {

    private String categorizationCode;

    private String name;

    private Integer sequence;

}
