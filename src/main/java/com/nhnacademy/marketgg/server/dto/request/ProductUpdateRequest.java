package com.nhnacademy.marketgg.server.dto.request;

import java.time.LocalDate;
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
    private String description;
    private String unit;
    private String deliveryType;
    private String origin;
    private String packageType;
    private LocalDate expirationDate;
    private String allergyInfo;
    private String capacity;

}
