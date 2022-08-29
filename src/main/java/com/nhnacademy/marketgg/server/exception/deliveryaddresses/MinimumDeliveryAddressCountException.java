package com.nhnacademy.marketgg.server.exception.deliveryaddresses;

public class MinimumDeliveryAddressCountException extends RuntimeException {
    private static final String ERROR = "배송지는 최소 1개 이상 있어야 합니다.";

    public MinimumDeliveryAddressCountException() {
        super(ERROR);
    }

}
