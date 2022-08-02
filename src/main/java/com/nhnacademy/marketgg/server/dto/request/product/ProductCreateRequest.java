package com.nhnacademy.marketgg.server.dto.request.product;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {

    private String categoryCode;
    private Long labelNo;
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
