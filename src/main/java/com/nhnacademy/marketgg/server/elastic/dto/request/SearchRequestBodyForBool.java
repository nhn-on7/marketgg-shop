package com.nhnacademy.marketgg.server.elastic.dto.request;

import com.nhnacademy.marketgg.server.elastic.dto.request.searchutil.Bool;
import com.nhnacademy.marketgg.server.elastic.dto.request.searchutil.BoolQuery;
import com.nhnacademy.marketgg.server.elastic.dto.request.searchutil.MultiMatch;
import com.nhnacademy.marketgg.server.elastic.dto.request.searchutil.Must;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

/**
 * 카테고리 내에서 검색 시, 해당 객체를 통해 요청을 진행합니다.
 *
 * @version 1.0.0
 */
@Getter
public class SearchRequestBodyForBool<T> {

    private static final List<String> CATEGORY_FIELD = List.of("categoryCode");
    private static final List<String> DEFAULT_PRODUCT_FIELD =
            List.of("productName", "productName.forSyno", "content", "content.forSyno",
                    "description", "description.forSyno");

    private static final List<String> DEFAULT_BOARD_FIELD =
            List.of("title", "title.forSyno");

    /**
     * 검색 결과 목록의 정렬 기준을 지정합니다.
     *
     * @since 1.0.0
     */
    private final List<T> sort;

    /**
     * 검색 결과 목록의 페이지 번호를 지정합니다.
     *
     * @since 1.0.0
     */
    private final Integer from;

    /**
     * 검색 결과 목록의 페이지 크기를 지정합니다.
     *
     * @since 1.0.0
     */
    private final Integer size;

    /**
     * 검색 기준을 지정합니다. 검색어 및 검색을 진행 할 필드를 지정할 수 있습니다.
     *
     * @since 1.0.0
     */
    private final BoolQuery query;

    /**
     * 조건을 담은 검색을 진행 할 수 있는 요청객체를 생성합니다.
     *
     * @param sortMap - 결과 목록의 정렬기준입니다.
     * @param request - 검색을 진행할 검색정보를 담은 객체입니다.
     * @param optionCode - 검색을 진행할 옵션 값입니다.
     * @param option - 검색을 진행할 옵션입니다.
     * @since 1.0.0
     */
    public SearchRequestBodyForBool(final String optionCode, final T sortMap,
                                    final SearchRequest request, final String option) {

        List<String> requestOption = DEFAULT_PRODUCT_FIELD;

        this.sort = Collections.singletonList(sortMap);
        this.from = request.getPage();
        this.size = request.getSize();
        if (Boolean.TRUE.equals(this.isBoard(option))) {
            requestOption = DEFAULT_BOARD_FIELD;
        }
        this.query = new BoolQuery(
                new Bool(List.of(new Must(new MultiMatch(optionCode, CATEGORY_FIELD)),
                                 new Must(new MultiMatch(request.getRequest(), requestOption)))));
    }

    /**
     * 조건을 담은 검색을 진행 할 수 있는 요청객체를 생성합니다.
     *
     * @param categoryCode - 검색을 진행할 카테고리의 식별번호입니다.
     * @param sortMap - 결과 목록의 정렬기준입니다.
     * @param request - 검색을 진행할 검색정보를 담은 객체입니다.
     * @param optionCode - 검색을 진행할 옵션 값입니다.
     * @param option - 검색을 진행할 옵션입니다.
     * @since 1.0.0
     */
    public SearchRequestBodyForBool(final String categoryCode, final T sortMap,
                                    final SearchRequest request, final String optionCode,
                                    final String option) {
        this.sort = Collections.singletonList(sortMap);
        this.from = request.getPage();
        this.size = request.getSize();
        this.query = new BoolQuery(new Bool(List.of(new Must(new MultiMatch(categoryCode, CATEGORY_FIELD)),
                                                    new Must(new MultiMatch(optionCode, List.of(option))),
                                                    new Must(new MultiMatch(request.getRequest(), DEFAULT_BOARD_FIELD)))));
    }

    private Boolean isBoard(final String document){
        return document.compareTo("board") == 0;
    }


}
