package com.nhnacademy.marketgg.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryRegisterRequest {

    private Long superCategoryNo;

    private String name;

    private Integer sequence;

    private String code;

    public static CategoryRegisterRequest of() {
        return new CategoryRegisterRequest(0L, "채소", 1, "PROD");
    }

}
