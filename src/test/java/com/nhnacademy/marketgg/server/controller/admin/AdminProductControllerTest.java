package com.nhnacademy.marketgg.server.controller.admin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.DefaultPageResult;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.service.product.ProductService;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AdminProductController.class)
class AdminProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    private static final String DEFAULT_PRODUCT = "/admin/products";
    private ProductCreateRequest productRequest;
    private ProductUpdateRequest productUpdateRequest;

    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        productRequest = Dummy.getDummyProductCreateRequest();
        productUpdateRequest = Dummy.getDummyProductUpdateRequest();
        httpHeaders = new HttpHeaders();
        httpHeaders.add(AspectUtils.AUTH_ID, UUID.randomUUID().toString());
        httpHeaders.add(AspectUtils.WWW_AUTHENTICATE, "[\"ROLE_ADMIN\"]");
    }

    @Test
    @DisplayName("상품 등록하는 테스트")
    void testCreateProduct() throws Exception {
        String content = objectMapper.writeValueAsString(productRequest);

        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        MockMultipartFile file =
                new MockMultipartFile("image", "lee.png", "image/png",
                                      new FileInputStream(filePath));

        MockMultipartFile dto =
                new MockMultipartFile("productRequest", "jsondata", "application/json",
                                      content.getBytes(StandardCharsets.UTF_8));

        this.mockMvc.perform(multipart(DEFAULT_PRODUCT).file(dto)
                                                       .file(file)
                                                       .headers(httpHeaders)
                                                       .contentType(
                                                               MediaType.APPLICATION_JSON_VALUE)
                                                       .contentType(
                                                               MediaType.MULTIPART_FORM_DATA_VALUE)
                                                       .characterEncoding(StandardCharsets.UTF_8))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", DEFAULT_PRODUCT));

        then(productService).should(times(1))
                            .createProduct(any(ProductCreateRequest.class), any(MockMultipartFile.class));
    }

    @Test
    @DisplayName("상품 목록 전체 조회하는 테스트")
    void testRetrieveProducts() throws Exception {
        PageRequest request = PageRequest.of(0, 5);

        given(productService.retrieveProducts(request)).willReturn(new DefaultPageResult<>());

        this.mockMvc.perform(get(DEFAULT_PRODUCT)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .headers(httpHeaders))
                    .andExpect(status().isOk());

        then(productService).should(times(1)).retrieveProducts(any());
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void testRetrieveProductDetails() throws Exception {
        given(productService.retrieveProductDetails(anyLong())).willReturn(new SingleResponse<>());

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/1")
                                     .headers(httpHeaders)
                                     .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        then(productService).should(times(1)).retrieveProductDetails(anyLong());
    }

    @Test
    @DisplayName("상품 정보 수정하는 테스트")
    void testUpdateProduct() throws Exception {
        String content = this.objectMapper.writeValueAsString(productUpdateRequest);
        MockMultipartFile dto =
                new MockMultipartFile("productRequest", "jsondata", "application/json",
                                      content.getBytes(StandardCharsets.UTF_8));

        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile file =
                new MockMultipartFile("image", "lee.png", "image/png",
                                      new FileInputStream(filePath));

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(DEFAULT_PRODUCT + "/{product}", 1L);

        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        this.mockMvc.perform(builder.file(dto)
                                    .file(file)
                                    .headers(httpHeaders)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                                    .characterEncoding(StandardCharsets.UTF_8))
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 삭제하는 테스트")
    void testDeleteProduct() throws Exception {
        willDoNothing().given(this.productService).deleteProduct(anyLong());

        this.mockMvc.perform(delete(DEFAULT_PRODUCT + "/{productId}", 1L)
                                     .headers(httpHeaders))
                    .andExpect(status().isOk());

        then(productService).should(times(1)).deleteProduct(anyLong());
    }

}
