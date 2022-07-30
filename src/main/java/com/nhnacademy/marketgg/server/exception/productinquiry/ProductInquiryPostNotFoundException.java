package com.nhnacademy.marketgg.server.exception.productinquiry;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 상품문의를 찾을 수 없을 때 예외처리입니다.
 *
 * @version 1.0.0
 */
public class ProductInquiryPostNotFoundException extends NotFoundException {

    private static final String ERROR = "상품 문의를 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public ProductInquiryPostNotFoundException() {
        super(ERROR);
    }

}
