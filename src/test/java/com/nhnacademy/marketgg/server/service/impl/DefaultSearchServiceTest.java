package com.nhnacademy.marketgg.server.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchBoardResponse;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.server.elastic.repository.SearchRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class DefaultSearchServiceTest {

    @InjectMocks
    private DefaultSearchService searchService;

    @Mock
    private SearchRepository searchRepository;

    private static SearchRequest searchRequest;
    private static SearchProductResponse searchProductResponse;
    private static SearchBoardResponse searchBoardResponse;

    @BeforeAll
    static void beforeAll() {
        searchRequest = new SearchRequest("hello", PageRequest.of(0, 5));
        searchProductResponse = new SearchProductResponse();
        searchBoardResponse = new SearchBoardResponse();
        ReflectionTestUtils.setField(searchProductResponse, "productName", "hello");
        ReflectionTestUtils.setField(searchBoardResponse, "title", "hello");
    }

    @Test
    @DisplayName("상품 카테고리 목록 내 검색")
    void testSearchProductForCategory() throws Exception {
        given(searchRepository.searchProductForCategory(anyString(),
                                                        any(SearchRequest.class),
                                                        any())).willReturn(
                List.of(searchProductResponse));

        searchService.searchProductForCategory("11", searchRequest, null);

        then(searchRepository).should()
                              .searchProductForCategory(anyString(), any(SearchRequest.class),
                                                        any());
    }

    @Test
    @DisplayName("상품 전체 목록 검색")
    void testSearchProductForKeyword() throws Exception {
        given(searchRepository.searchProductWithKeyword(
                any(SearchRequest.class), any())).willReturn(List.of(searchProductResponse));

        searchService.searchProductForKeyword(searchRequest, null);

        then(searchRepository).should().searchProductWithKeyword(any(SearchRequest.class), any());
    }

    @Test
    @DisplayName("상품 카테고리 목록 내 가격별 정렬 검색")
    void testSearchProductForCategorySortPrice() throws Exception {
        given(searchRepository.searchProductForCategory(anyString(),
                                                        any(SearchRequest.class),
                                                        anyString())).willReturn(
                List.of(searchProductResponse));

        searchService.searchProductForCategory("11", searchRequest, "desc");

        then(searchRepository).should().searchProductForCategory(
                anyString(), any(SearchRequest.class), anyString());
    }

    @Test
    @DisplayName("상품 전체 목록 내 가격별 정렬 검색")
    void testSearchProductForKeywordSortPrice() throws Exception {
        given(searchRepository.searchProductWithKeyword(any(SearchRequest.class),
                                                        anyString())).willReturn(
                List.of(searchProductResponse));

        searchService.searchProductForKeyword(searchRequest, "desc");

        then(searchRepository).should()
                              .searchProductWithKeyword(any(SearchRequest.class), anyString());
    }

    @Test
    @DisplayName("게시판 카테고리 별 검색")
    void testSearchBoardForCategory() throws Exception {
        given(searchRepository.searchBoardWithCategoryCode(anyString(), any(SearchRequest.class),
                                                           anyString()))
                .willReturn(List.of(searchBoardResponse));

        searchService.searchBoardForCategory("11", searchRequest, "환불");

        then(searchRepository).should()
                              .searchBoardWithCategoryCode(anyString(), any(SearchRequest.class),
                                                           anyString());
    }

    @Test
    @DisplayName("게시판 카테고리 내 옵션 별 검색")
    void testSearchBoardForOption() throws Exception {
        given(searchRepository.searchBoardWithOption(anyString(), anyString(),
                                                     any(SearchRequest.class),
                                                     anyString())).willReturn(
                List.of(searchBoardResponse));

        searchService.searchBoardForOption("11", "공지사항", searchRequest, "categoryCode");

        then(searchRepository).should()
                              .searchBoardWithOption(anyString(), anyString(),
                                                     any(SearchRequest.class),
                                                     anyString());
    }

}
