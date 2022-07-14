package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ProductService productService;

    HttpHeaders headers;
    String DEFAULT_PRODUCT = "/admin/v1/products";
    String uploadPath = "/Users/coalong/Downloads";
    static ProductCreateRequest productRequest;


    @BeforeAll
    static void beforeAll() {
        productRequest = new ProductCreateRequest();
        ReflectionTestUtils.setField(productRequest, "name", "자몽");
        ReflectionTestUtils.setField(productRequest, "content", "아침에 자몽 쥬스");
        ReflectionTestUtils.setField(productRequest, "totalStock", 100L);
        ReflectionTestUtils.setField(productRequest, "price", 2000L);
    }

    // @Test
    // @DisplayName("상품 등록하는 테스트")
    // void testCreateProduct() throws Exception {
    //     doNothing().when(productService).createProduct(any(), any());
    //     String content = objectMapper.writeValueAsString(productRequest);
    //
    //     headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);
    //     headers.setLocation(URI.create(DEFAULT_PRODUCT));
    //
    //     this.mockMvc.perform(post("/admin/v1/products")
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(content))
    //                 .andExpect(status().isCreated())
    //                 .andExpect(header().string("Location", DEFAULT_PRODUCT));
    //
    //     verify(productService, times(1)).createProduct(any(productRequest.getClass()), any());
    // }

    @Test
    @DisplayName("상품 목록 전체 조회하는 테스트")
    void testRetrieveProducts() throws Exception {
        when(productService.retrieveProducts()).thenReturn(List.of());

        this.mockMvc.perform(get("/admin/v1/products")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        verify(productService, times(1)).retrieveProducts();
    }

    @Test
    @DisplayName("상품 정보 수정하는 테스트")
    void testUpdateProduct() throws Exception {

        doNothing().when(productService)
                   .updateProduct(any(ProductUpdateRequest.class), any(MockMultipartFile.class), anyLong());

        String content = objectMapper.writeValueAsString(productRequest);
        MockMultipartFile dto = new MockMultipartFile("productRequest", "jsondata", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        MockMultipartFile file = new MockMultipartFile("image", "test.png", "image/png",
                new FileInputStream(uploadPath + "/marketGG-로고.png"));

        this.mockMvc.perform(multipart("/admin/v1/products/{productId}", 1L)
                    .file(dto)
                    .file(file)
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .characterEncoding(StandardCharsets.UTF_8))
                    .andExpect(status().isOk());
        verify(productService, times(1)).updateProduct(any(), any(), any());
    }

    @Test
    @DisplayName("상품 삭제하는 테스트")
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        this.mockMvc.perform(post("/admin/v1/products/{productId}/deleted", 1L))
                    .andExpect(status().isOk());
        verify(productService, times(1)).deleteProduct(anyLong());
    }

    @Test
    @DisplayName("상품 검색하는 테스트")
    void testSearchProductsByName() throws Exception {
        when(productService.searchProductsByName(anyString())).thenReturn(List.of());

        this.mockMvc.perform(get("/admin/v1/products/search/{productName}", "오렌지")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(productService, times(1)).searchProductsByName(anyString());
    }

}
