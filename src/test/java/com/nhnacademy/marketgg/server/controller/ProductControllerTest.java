package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.controller.product.ProductController;
import com.nhnacademy.marketgg.server.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    @DisplayName("카테고리로 상품 검색 테스트")
    void testSearchProductsByCategory() throws Exception {
        this.mockMvc.perform(
                    MockMvcRequestBuilders.get(DEFAULT_PRODUCT + "/categories/{categoryCode}", "100")
                            .param("page", "1")
                            .param("size", "0"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(productService).should().searchProductByCategory(any(PageRequest.class), anyString());
    }

}
