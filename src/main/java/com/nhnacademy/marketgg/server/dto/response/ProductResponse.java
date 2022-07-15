package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Category;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProductResponse {

    Long getId();

    Asset getAsset();

    Category getCategory();

    String getName();

    String getContent();

    Long getTotalStock();

    Long getPrice();

    String getDescription();

    String getUnit();

    String getDeliveryType();

    String getOrigin();

    String getPackageType();

    LocalDate getExpirationDate();

    String getAllergyInfo();

    String getCapacity();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    LocalDateTime getDeletedAt();

}
