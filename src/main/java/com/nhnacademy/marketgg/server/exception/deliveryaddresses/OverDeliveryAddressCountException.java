package com.nhnacademy.marketgg.server.exception.deliveryaddresses;

public class OverDeliveryAddressCountException extends RuntimeException {

    private static final String ERROR = "배송지는 최대 3개를 넘을 수 없습니다.";

    /**
     * 예외처리 시, 지정한 메세지를 보냅니다.
     *
     * @since 1.0.0
     */
    public OverDeliveryAddressCountException() {
        super(ERROR);
    }
}
