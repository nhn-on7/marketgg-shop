package com.nhnacademy.marketgg.server.elastic.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
     * 사용자가 검색을 진행 할 카테고리 식별번호입니다.
     *
     * @since 1.0.0
     */
    @NotBlank
    @Size(min = 1, max = 6)
    private String categoryCode;

    /**
     * 사용자의 검색 키워드입니다.
     *
     * @since 1.0.0
     */
    @NotBlank
    @Size(min = 1)
    private String request;

    /**
     * 반환 할 페이지 정보입니다.
     *
     * @since 1.0.0
     */
    @NotNull
    @Min(0)
    private Integer page;

    /**
     * 사용자가 검색할 페이지의 크기 정보입니다.
     *
     * @since 1.0.0
     */
    @NotNull
    private Integer size;

}
