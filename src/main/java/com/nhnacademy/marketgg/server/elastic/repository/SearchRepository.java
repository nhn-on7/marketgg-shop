package com.nhnacademy.marketgg.server.elastic.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchProductResponse;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 * 검색 Repository 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface SearchRepository {

    /**
     * 지정한 카테고리에서 상품에 대한 검색을 할 수 있는 메소드입니다.
     *
     * @param code          - 지정한 카테고리의 식별번호입니다.
     * @param request       - 검색을 진행 할 조건을 담은 객체입니다.
     * @param priceSortType - 가격의 정렬 타입입니다. (asc, desc)
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductForCategory(final String code,
                                                         final SearchRequest request,
                                                         final String priceSortType)
        throws JsonProcessingException, ParseException;

    /**
     * 전체 목록에서 상품에 대한 검색을 할 수 있는 메소드입니다.
     *
     * @param request       - 검색을 진행 할 조건을 담은 객체입니다.
     * @param priceSortType - 가격의 정렬 타입입니다. (asc, desc)
     * @return 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request,
                                                         final String priceSortType)
        throws JsonProcessingException, ParseException;

    /**
     * 지정한 카테고리에 대한 게시글 목록에서 검색을 할 수 있는 메소드입니다.
     *
     * @param categoryCode - 검색을 진행 할 카테고리의 식별값입니다.
     * @param request      - 검색을 진행 할 조건을 담은 객체입니다.
     * @param option       - 검색을 진행 할 옵션입니다.
     * @return 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<PostResponse> searchBoardWithCategoryCode(final String categoryCode,
                                                   final SearchRequest request,
                                                   final String option)
        throws JsonProcessingException, ParseException;

    /**
     * 지정한 카테고리 내의 지정한 옵션으로 검색을 할 수 있는 메소드입니다.
     *
     * @param categoryCode - 검색을 진행 할 카테고리의 식별값입니다.
     * @param optionCode   - 검색을 진행 할 옵션의 값입니다.
     * @param request      - 검색을 진행 할 조건을 담은 객체입니다.
     * @param option       - 검색을 진행 할 옵션입니다.
     * @return 지정한 카테고리 내에서 지정한 옵션의 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<PostResponse> searchBoardWithOption(final String categoryCode,
                                             final String optionCode,
                                             final SearchRequest request,
                                             final String option)
        throws JsonProcessingException, ParseException;

}
