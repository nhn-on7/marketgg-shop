package com.nhnacademy.marketgg.server.constant;

/**
 * 고객센터 게시글의 상태 Enum 클래스입니다.
 *
 * @version 1.0.0
 */
public enum OtoStatus {

    UNANSWERED("미답변"),
    ANSWERING("답변중"),
    ANSWERED("답변완료");

    private final String status;

    OtoStatus(String status) {
        this.status = status;
    }

    public String status() {
        return this.status;
    }
}
