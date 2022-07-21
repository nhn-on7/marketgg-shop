package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ProductResponse {

    private final Long id;

    private final Asset asset;

    private final Category category;

    private final String categoryCode;

    private final String categoryName;

    private final String name;

    private final String content;

    private final Long totalStock;

    private final Long price;

    private final String description;

    private final String unit;

    private final String deliveryType;

    private final String origin;

    private final String packageType;

    private final LocalDate expirationDate;

    private final String allergyInfo;

    private final String capacity;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final LocalDateTime deletedAt;

}
