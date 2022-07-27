package com.nhnacademy.marketgg.server.exception;

public class ProductInCartNotFoundException extends IllegalArgumentException {

    private static final String MESSAGE = "해당 상품을 장바구니에서 찾을 수 없습니다.";

    public ProductInCartNotFoundException() {
        super(MESSAGE);
    }

}
