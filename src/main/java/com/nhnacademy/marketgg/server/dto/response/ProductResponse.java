package com.nhnacademy.marketgg.server.dto.response;

public interface ProductResponse {

    Long getProductNo();

    Long getCategoryNo();

    String getName();

    String getContent();

    Long getTotalStock();

    Long getPrice();

    String getThumbnail();

}
