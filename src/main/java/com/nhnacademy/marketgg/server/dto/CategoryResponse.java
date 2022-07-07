package com.nhnacademy.marketgg.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryResponse {

    private final Long categoryNo;

    private final String name;

    private final String code;

}
