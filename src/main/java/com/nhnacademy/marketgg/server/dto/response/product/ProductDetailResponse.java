package com.nhnacademy.marketgg.server.dto.response.product;

import com.nhnacademy.marketgg.server.entity.Asset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductDetailResponse {

    private final Long id;

    private final Asset asset;

    private final Long assetNo;

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

    private final String imageAddress;

    private boolean dib;

    public void updateIsDib(final Boolean isDib) {
        this.dib = isDib;
    }

}
