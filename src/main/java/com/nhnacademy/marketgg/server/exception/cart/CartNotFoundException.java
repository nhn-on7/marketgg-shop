package com.nhnacademy.marketgg.server.exception.cart;

import com.nhnacademy.marketgg.server.exception.NotFoundException;

/**
 * 장바구니를 찾을 수 없을 때 예외처리입니다.
 *
 * @version 1.0.0
 */
public class CartNotFoundException extends NotFoundException {

    private static final String ERROR = "장바구니를 찾을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public CartNotFoundException() {
        super(ERROR);
    }

    public static class ProductInCartNotFoundException extends IllegalArgumentException {

        private static final String MESSAGE = "해당 상품을 장바구니에서 찾을 수 없습니다.";

        public ProductInCartNotFoundException() {
            super(MESSAGE);
        }

    }
}
