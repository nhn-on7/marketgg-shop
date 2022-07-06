package com.nhnacademy.marketgg.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {

    private Long categoryNo;
    private String name;
    private String content;
    private Long totalStock;
    private Long price;
    private String thumbnail;

}
