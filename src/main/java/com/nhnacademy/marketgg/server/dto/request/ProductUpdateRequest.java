package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductUpdateRequest {

    private Long productNo;
    private Long categoryNo;
    private String name;
    private String content;
    private Long totalStock;
    private Long price;
    private String thumbnail;
}
