package com.nhnacademy.marketgg.server.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 에서 제공하는 오류에 대한 정보를 담고 있는 클래스입니다.
 */
@RequiredArgsConstructor
@Getter
public class ErrorEntity {

    private final String message;

}
