package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.controller.product.ProductController;
import com.nhnacademy.marketgg.server.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    private static final String DEFAULT_PRODUCT = "/products";

    @Test
    @DisplayName("카테고리로 상품 조회 테스트")
    void testFindProductsByCategory() throws Exception {
        given(productService.findProductByCategory(any(PageRequest.class), anyString())).willReturn(List.of());

        this.mockMvc.perform(
                    get(DEFAULT_PRODUCT + "/categories/{categoryCode}", "100")
                            .param("page", "1")
                            .param("size", "0"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(productService).should(times(1)).findProductByCategory(any(PageRequest.class), anyString());
    }

    @Test
    @DisplayName("전체 목록에서 상품 검색 테스트")
    void testSearchProductList() throws Exception {
        given(productService.searchProductList(anyString(), anyInt())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/search")
                                     .param("keyword", "hi")
                                     .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(productService).should(times(1)).searchProductList(anyString(), anyInt());
    }

    @Test
    @DisplayName("카테고리 목록 내에서 상품 검색 테스트")
    void testSearchProductListByCategory() throws Exception {
        given(productService.searchProductListByCategory(anyString(), anyString(), anyInt())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/categories/{categoryId}/search", "100")
                                     .param("keyword", "hi")
                                     .param("page", "1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(productService).should(times(1)).searchProductListByCategory(anyString(), anyString(), anyInt());
    }

    @Test
    @DisplayName("카테고리 목록 내에서 선택한 옵션별로 정렬 하는 상품 검색 테스트")
    void testSearchProductListByPrice() throws Exception {
        given(productService.searchProductListByPrice(anyString(), anyString(), anyString(), anyInt())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/categories/{categoryId}/price/{option}/search", "100", "desc")
                                     .param("keyword", "hi")
                                     .param("page", "1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(productService).should(times(1)).searchProductListByPrice(anyString(), anyString(), anyString(), anyInt());
    }

}
