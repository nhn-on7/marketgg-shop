package com.nhnacademy.marketgg.server.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 서비스에서 요청에 대한 응답 결과를 페이지 정보와 함께 제공하는 클래스입니다.
 *
 * @param <T> - 페이징 처리가 되는 요소의 응답 정보 타입
 */
@RequiredArgsConstructor
@Getter
public class PageEntity<T> {

    private final Integer pageNumber;

    private final Integer pageSize;

    private final Integer totalPages; // 총 페이지 수

    private final List<T> data;

}
