package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.response.ProductResponse;
import com.nhnacademy.marketgg.server.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    private ProductResponse productResponse;


    private static final String DEFAULT_PRODUCT = "/shop/v1/products";

    @BeforeEach
    void setUp() {
        productResponse = new ProductResponse(null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null, null);
    }

    // @Test
    // @DisplayName("카테고리로 상품 검색 테스트")
    // void testSearchProductsByCategory() throws Exception {
    //     BDDMockito.given(this.productService.searchProductByCategory(any()))
    //               .willReturn(List.of(productResponse));
    //
    //     this.mockMvc.perform(
    //             MockMvcRequestBuilders.get(DEFAULT_PRODUCT + "/categories/" + "{categoryCode}", 1L))
    //                 .andExpect(status().isOk())
    //                 .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    //
    //     BDDMockito.verify(productService, Mockito.atLeastOnce()).searchProductByCategory(anyString());
    // }

}
