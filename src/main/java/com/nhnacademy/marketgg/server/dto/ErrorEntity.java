package com.nhnacademy.marketgg.server.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 에서 제공하는 오류에 대한 클래스입니다.
 *
 * @author 김정민
 * @author 김훈민
 * @author 민아영
 * @author 박세완
 * @author 윤동열
 * @author 이제훈
 * @author 조현진
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class ErrorEntity {

    private final String message;

}