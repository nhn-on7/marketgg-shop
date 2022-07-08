package com.nhnacademy.marketgg.server.dto.response;

import java.time.LocalDate;

public interface ProductResponse {

    Long getProductNo();

    Long getCategoryNo();

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

}
