package com.nhnacademy.marketgg.server.constant;

public enum CustomerServicePostStatus {

    UNANSWERED("미답변"),
    ANSWERING("답변중"),
    ANSWERED("답변완료");

    private final String status;

    CustomerServicePostStatus(String status) {
        this.status = status;
    }

    public String status() {
        return this.status;
    }
}
