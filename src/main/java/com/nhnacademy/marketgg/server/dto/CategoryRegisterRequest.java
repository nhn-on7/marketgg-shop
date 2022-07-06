package com.nhnacademy.marketgg.server.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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

}
