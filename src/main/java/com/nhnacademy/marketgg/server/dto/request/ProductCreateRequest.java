package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ProductCreateRequest {

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
