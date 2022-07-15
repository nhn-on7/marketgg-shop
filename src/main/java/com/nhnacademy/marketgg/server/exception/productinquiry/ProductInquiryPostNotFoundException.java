package com.nhnacademy.marketgg.server.exception.productinquiry;

public class ProductInquiryPostNotFoundException extends IllegalArgumentException {

    private static final String ERROR = "상품 문의를 찾을 수 없습니다.";

    public ProductInquiryPostNotFoundException() {
        super(ERROR);
    }

}
