package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ProductUpdateRequest {

    private Long productNo;
    private Long assetNo;
    private String categoryCode;
    private String name;
    private String content;
    private Long totalStock;
    private Long price;
    private String description;
    private String unit;
    private String deliveryType;
    private String origin;
    private String packageType;
    private LocalDate expirationDate;
    private String allergyInfo;
    private String capacity;

}
