package com.nhnacademy.marketgg.server.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PointContent {

    REFERRED("추천인 적립"),
    NORMAL_REVIEW("일반 리뷰 적립"),
    IMAGE_REVIEW("포토 리뷰 적립"),
    ORDER("주문 적립"),
    ORDER_CANCEL("주문 취소"),
    EVENT("이벤트 적립"),
    ETC("기타");

    private final String content;

}
