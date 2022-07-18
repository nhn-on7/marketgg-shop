package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.service.ProductService;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
@ActiveProfiles("local")
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    private static final String DEFAULT_PRODUCT = "/admin/v1/products";
    private static ProductCreateRequest productRequest;

    @BeforeAll
    static void beforeAll() {
        productRequest = new ProductCreateRequest();
    }

    @Test
    @DisplayName("상품 등록하는 테스트")
    void testCreateProduct() throws Exception {

        String content = this.objectMapper.writeValueAsString(productRequest);

        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        MockMultipartFile file =
            new MockMultipartFile("image", "lee.png", "image/png", new FileInputStream(filePath));

        MockMultipartFile dto = new MockMultipartFile("productRequest", "jsondata", "application/json",
            content.getBytes(StandardCharsets.UTF_8));

        this.mockMvc.perform(multipart("/admin/v1/products").file(dto)
                                                            .file(file)
                                                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                                            .characterEncoding(StandardCharsets.UTF_8))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", DEFAULT_PRODUCT));

        BDDMockito.verify(this.productService, times(1))
                  .createProduct(any(ProductCreateRequest.class), any(MockMultipartFile.class));
    }

    @Test
    @DisplayName("상품 목록 전체 조회하는 테스트")
    void testRetrieveProducts() throws Exception {
        given(productService.retrieveProducts()).willReturn(List.of());

        this.mockMvc.perform(get("/admin/v1/products").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        verify(this.productService, times(1)).retrieveProducts();
    }

    @Test
    @DisplayName("상품 정보 수정하는 테스트")
    void testUpdateProduct() throws Exception {
        String content = this.objectMapper.writeValueAsString(productRequest);
        MockMultipartFile dto = new MockMultipartFile("productRequest", "jsondata", "application/json",
            content.getBytes(StandardCharsets.UTF_8));

        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile file =
            new MockMultipartFile("image", "lee.png", "image/png", new FileInputStream(filePath));

        this.mockMvc.perform(multipart("/admin/v1/products/{productId}", 1L).file(dto)
                                                                            .file(file)
                                                                            .contentType(
                                                                                MediaType.APPLICATION_JSON)
                                                                            .contentType(
                                                                                MediaType.MULTIPART_FORM_DATA)
                                                                            .characterEncoding(
                                                                                StandardCharsets.UTF_8))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 삭제하는 테스트")
    void testDeleteProduct() throws Exception {
        doNothing().when(this.productService).deleteProduct(anyLong());

        this.mockMvc.perform(post("/admin/v1/products/{productId}/deleted", 1L)).andExpect(status().isOk());
        verify(this.productService, times(1)).deleteProduct(anyLong());
    }

    @Test
    @DisplayName("상품 검색하는 테스트")
    void testSearchProductsByName() throws Exception {
        when(this.productService.searchProductsByName(anyString())).thenReturn(List.of());

        this.mockMvc.perform(
                get("/admin/v1/products/search/{productName}", "오렌지").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(this.productService, times(1)).searchProductsByName(anyString());

    }
}
