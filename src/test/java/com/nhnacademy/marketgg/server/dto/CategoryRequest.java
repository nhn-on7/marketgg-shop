package com.nhnacademy.marketgg.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryRequest {

    private Long superCategoryNo;

    private String name;

    private Integer sequence;

    private String code;

    public static CategoryRequest of() {
        return new CategoryRequest(0L, "채소", 1, "PROD");
    }

}
