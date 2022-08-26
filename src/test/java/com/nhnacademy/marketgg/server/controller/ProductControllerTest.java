package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.controller.product.ProductController;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.response.product.ProductDetailResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.ProductListResponse;
import com.nhnacademy.marketgg.server.service.product.ProductService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    private SearchRequest searchRequest;

    private static final String DEFAULT_PRODUCT = "/products";

    private ProductDetailResponse productDetailResponse;

    private Page<ProductListResponse>  page;
    @BeforeEach
    void setUp() {
        searchRequest = new SearchRequest();

        ReflectionTestUtils.setField(searchRequest, "categoryCode", "702");
        ReflectionTestUtils.setField(searchRequest, "keyword", "hi");
        ReflectionTestUtils.setField(searchRequest, "page", 0);
        ReflectionTestUtils.setField(searchRequest, "size", 10);

        productDetailResponse = Dummy.getDummyProductResponse();
        page = Dummy.getDummyProductPage();
    }

    @Test
    @DisplayName("전체 목록에서 상품 검색 테스트")
    void testSearchProductList() throws Exception {
        given(productService.searchProductList(any(SearchRequest.class))).willReturn(
            new PageEntity<>(0, 10, 1, List.of()));

        this.mockMvc.perform(post(DEFAULT_PRODUCT + "/search")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(searchRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(productService).should(times(1)).searchProductList(any(SearchRequest.class));
    }

    @Test
    @DisplayName("카테고리 목록 내에서 상품 검색 테스트")
    void testSearchProductListByCategory() throws Exception {
        given(productService.searchProductListByCategory(any(SearchRequest.class))).willReturn(
            new PageEntity<>(0, 10, 1, List.of()));

        this.mockMvc.perform(post(DEFAULT_PRODUCT + "/categories/{categoryId}/search", "100")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(searchRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(productService).should(times(1)).searchProductListByCategory(any(SearchRequest.class));
    }

    @Test
    @DisplayName("카테고리 목록 내에서 선택한 옵션별로 정렬 하는 상품 검색 테스트")
    void testSearchProductListByPrice() throws Exception {
        given(productService.searchProductListByPrice(anyString(), any(SearchRequest.class))).willReturn(
            new PageEntity<>(0, 10, 1, List.of()));

        this.mockMvc.perform(post(DEFAULT_PRODUCT + "/categories/{categoryId}/sort-price/{option}/search",
                                  "100",
                                  "desc")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(searchRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(productService).should(times(1)).searchProductListByPrice(anyString(), any(SearchRequest.class));
    }

    @Test
    @DisplayName("상품 목록 전체 조회하는 테스트")
    void testRetrieveProducts() throws Exception {
        given(productService.retrieveProducts(any(Pageable.class))).willReturn(page);

        this.mockMvc.perform(get(DEFAULT_PRODUCT)
                                 .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        then(productService).should(times(1)).retrieveProducts(any());
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void testRetrieveProductDetails() throws Exception {
        given(productService.retrieveProductDetails(anyLong())).willReturn(productDetailResponse);

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/1")
                                 .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        then(productService).should(times(1)).retrieveProductDetails(anyLong());
    }

}
