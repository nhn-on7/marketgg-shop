package com.nhnacademy.marketgg.server.constant;

public enum OrderStatus {

    WAITING("결제대기"),
    COMPLETE("결제완료");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String status() {
        return this.status;
    }

}
