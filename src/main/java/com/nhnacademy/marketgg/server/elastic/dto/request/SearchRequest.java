package com.nhnacademy.marketgg.server.elastic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자의 검색 및 반환 할 페이지를 표시합니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class SearchRequest {

    /**
     * 사용자의 검색 키워드입니다.
     *
     * @since 1.0.0
     */
    private String request;

    /**
     * 반환 할 페이지 정보입니다.
     *
     * @since 1.0.0
     */
    private Integer page;

    private Integer size;

}
