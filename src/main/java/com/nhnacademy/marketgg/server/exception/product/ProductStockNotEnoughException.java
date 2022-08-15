package com.nhnacademy.marketgg.server.exception.product;

/**
 * 상품의 재고가 충분하지 않을 때 예외처리입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
public class ProductStockNotEnoughException extends RuntimeException {

    private static final String ERROR = "상품의 재고가 충분하지 않습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public ProductStockNotEnoughException() {
        super(ERROR);
    }
}
