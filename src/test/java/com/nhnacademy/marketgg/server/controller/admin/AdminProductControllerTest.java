package com.nhnacademy.marketgg.server.controller.admin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.dto.request.product.ProductCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.server.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductResponse;
import com.nhnacademy.marketgg.server.service.product.ProductService;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

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
    private ProductResponse productResponse;

    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add(AspectUtils.AUTH_ID, UUID.randomUUID().toString());
        httpHeaders.add(AspectUtils.WWW_AUTHENTICATE, "[\"ROLE_ADMIN\"]");

        productRequest = new ProductCreateRequest();
        ReflectionTestUtils.setField(productRequest, "categoryCode", "001");
        productResponse =
            new ProductResponse(null, null, null, null, null, null, null, null, null, null,
                null, null, null,
                null, null, null, null, null, null, null);
    }

    @Test
    @DisplayName("상품 등록하는 테스트")
    void testCreateProduct() throws Exception {
        String content = this.objectMapper.writeValueAsString(productRequest);

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

        BDDMockito.verify(this.productService, times(1))
                  .createProduct(any(ProductCreateRequest.class), any(MockMultipartFile.class));
    }

    // @Test
    // @DisplayName("상품 목록 전체 조회하는 테스트")
    // void testRetrieveProducts() throws Exception {
    //     PageRequest request = PageRequest.of(0, 5);
    //
    //     given(productService.retrieveProducts(request)).willReturn(new SingleResponse<>());
    //
    //     this.mockMvc.perform(get(DEFAULT_PRODUCT).contentType(MediaType.APPLICATION_JSON))
    //                 .andExpect(status().isOk());
    //
    //     then(this.productService).should().retrieveProducts(any());
    // }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void testRetrieveProductDetails() throws Exception {
        given(productService.retrieveProductDetails(anyLong())).willReturn(new SingleResponse<>());

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/1")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        then(this.productService).should().retrieveProductDetails(anyLong());
    }

    @Test
    @DisplayName("상품 정보 수정하는 테스트")
    void testUpdateProduct() throws Exception {
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        ReflectionTestUtils.setField(productUpdateRequest, "categoryCode", "001");

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
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
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
        doNothing().when(this.productService).deleteProduct(anyLong());

        this.mockMvc.perform(delete(DEFAULT_PRODUCT + "/{productId}", 1L)
                .headers(httpHeaders))
                    .andExpect(status().isOk());
        verify(this.productService, times(1)).deleteProduct(anyLong());
    }

}
