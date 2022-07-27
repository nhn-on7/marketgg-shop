package com.nhnacademy.marketgg.server.constant;

/**
 * 고객센터 게시글의 사유 Enum 클래스입니다.
 *
 * @version 1.0.0
 */
public enum CustomerServicePostReason {

    DELIVERY("배송"),
    PACKAGING("포장"),
    CANCEL("취소/교환/환불"),
    EVENT("이벤트/쿠폰/적립금"),
    PRODUCT("상품"),
    ORDER("주문/결제"),
    MEMBER("회원"),
    SERVICE("서비스이용"),
    ETC("기타");

    private final String reason;

    CustomerServicePostReason(String reason) {
        this.reason = reason;
    }

    public String reason() {
        return this.reason;
    }
}
