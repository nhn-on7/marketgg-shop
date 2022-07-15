package com.nhnacademy.marketgg.server.exception.product;

public class ProductNotFoundException extends RuntimeException {

    private static final String ERROR = "상품을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        super(ERROR);
    }

}
