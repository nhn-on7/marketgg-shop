package com.nhnacademy.marketgg.server.elastic.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자의 검색 및 반환 할 페이지를 표시합니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class SearchRequest {

    /**
     * 사용자가 검색을 진행 할 카테고리 식별번호입니다.
     *
     * @since 1.0.0
     */
    @Schema(title = "카테고리 식별번호", description = "검색을 진행 할 카테고리의 식별번호입니다.", example = "700")
    @NotBlank
    @Size(min = 1, max = 6)
    private String categoryCode;

    /**
     * 사용자의 검색 키워드입니다.
     *
     * @since 1.0.0
     */
    @Schema(title = "검색어", description = "검색을 진행 할 검색어입니다.", example = "계란")
    @NotBlank
    @Size(min = 1)
    private String keyword;

    /**
     * 반환 할 페이지 정보입니다.
     *
     * @since 1.0.0
     */
    @Schema(title = "페이지 번호", description = "조회할 페이지 번호입니다.", example = "0")
    @NotNull
    @Min(0)
    private Integer page;

    /**
     * 사용자가 검색할 페이지의 크기 정보입니다.
     *
     * @since 1.0.0
     */
    @Schema(title = "페이지 크기", description = "조회할 페이지 크기입니다.", example = "10")
    @NotNull
    private Integer size;

}
