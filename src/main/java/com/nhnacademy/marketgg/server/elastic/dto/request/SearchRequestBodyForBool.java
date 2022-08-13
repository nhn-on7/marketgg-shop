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
 * @author 박세완
 * @version 1.0.0
 */
@Getter
public class SearchRequestBodyForBool<T> {

    private static final List<String> CATEGORY_FIELD = List.of("categoryCode");
    private static final List<String> DEFAULT_PRODUCT_FIELD =
            List.of("productName", "productName.forSyno", "content", "content.forSyno",
                    "description", "description.forSyno");
    private static final String NO_FUZZINESS = "0";
    private static final String FUZZINESS = "AUTO";

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
     * @param sortMap    - 결과 목록의 정렬기준입니다.
     * @param request    - 검색을 진행할 검색정보를 담은 객체입니다.
     * @param convertString - 검색 시 한/영 오타 교정 단어입니다.
     * @param optionCode - 검색을 진행할 옵션 값입니다.
     * @param option     - 검색을 진행할 옵션입니다.
     * @since 1.0.0
     */
    public SearchRequestBodyForBool(final String optionCode, final T sortMap,
                                    final SearchRequest request, final String convertString, final String option) {

        List<String> requestOption = DEFAULT_PRODUCT_FIELD;

        this.sort = Collections.singletonList(sortMap);
        this.from = request.getPage();
        this.size = request.getSize();
        if (Boolean.TRUE.equals(this.isBoard(option))) {
            requestOption = DEFAULT_BOARD_FIELD;
        }
        this.query = new BoolQuery(
                new Bool(new Must(List.of(new MultiMatch(optionCode, NO_FUZZINESS, CATEGORY_FIELD),
                                          new MultiMatch(request.getRequest(), FUZZINESS, requestOption),
                                          new MultiMatch(convertString, NO_FUZZINESS, requestOption)))));
    }

    /**
     * 조건을 담은 검색을 진행 할 수 있는 요청객체를 생성합니다.
     *
     * @param categoryCode  - 검색을 진행할 카테고리의 식별번호입니다.
     * @param sortMap       - 결과 목록의 정렬기준입니다.
     * @param request       - 검색을 진행할 검색정보를 담은 객체입니다.
     * @param convertString - 검색 시 한/영 오타 교정 단어입니다.
     * @param optionCode    - 검색을 진행할 옵션 값입니다.
     * @param option        - 검색을 진행할 옵션입니다.
     * @since 1.0.0
     */
    public SearchRequestBodyForBool(final String categoryCode, final T sortMap, final SearchRequest request,
                                    final String convertString, final String optionCode, final String option) {

        this.sort = Collections.singletonList(sortMap);
        this.from = request.getPage();
        this.size = request.getSize();
        this.query = new BoolQuery(
                new Bool(new Must(List.of(new MultiMatch(categoryCode, NO_FUZZINESS, CATEGORY_FIELD),
                                 new MultiMatch(optionCode, NO_FUZZINESS, List.of(option)),
                                 new MultiMatch(request.getRequest(), FUZZINESS, DEFAULT_BOARD_FIELD),
                                 new MultiMatch(convertString, NO_FUZZINESS, DEFAULT_BOARD_FIELD)))));
    }

    /**
     * 조건없이 검색할 시 검색 요청 객체를 생성해줍니다.
     *
     * @param sortMap - 검색의 정렬 기준입니다.
     * @param request - 검색을 진행 할 정보를 담은 객체입니다.
     * @param convertString - 검색 시 한/영 오타 교정 단어입니다.
     * @since 1.0.0
     */
    public SearchRequestBodyForBool(final T sortMap, final SearchRequest request, final String convertString) {
        this.sort = Collections.singletonList(sortMap);
        this.from = request.getPage();
        this.size = request.getSize();
        this.query = new BoolQuery(
                new Bool(new Must(List.of(new MultiMatch(request.getRequest(), FUZZINESS, DEFAULT_PRODUCT_FIELD),
                                          new MultiMatch(convertString, NO_FUZZINESS, DEFAULT_BOARD_FIELD)))));
    }

    private Boolean isBoard(final String document) {
        return document.compareTo("board") == 0;
    }

}
