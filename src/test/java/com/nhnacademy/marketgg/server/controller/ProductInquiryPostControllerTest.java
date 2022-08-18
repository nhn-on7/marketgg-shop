package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import com.nhnacademy.marketgg.server.controller.product.ProductInquiryPostController;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.service.product.ProductInquiryPostService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductInquiryPostController.class)
class ProductInquiryPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductInquiryPostService productInquiryPostService;

    Pageable pageable = PageRequest.of(0, 20);
    Page<ProductInquiryResponse> responses = new PageImpl<>(List.of(), pageable, 0);

    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add(AspectUtils.AUTH_ID, UUID.randomUUID().toString());
        httpHeaders.add(AspectUtils.WWW_AUTHENTICATE, "[\"ROLE_ADMIN\"]");

    }

    @Test
    @DisplayName("상품 문의 등록 테스트")
    void testCreateProductInquiry() throws Exception {
        ProductInquiryRequest productInquiryRequest = new ProductInquiryRequest();
        ReflectionTestUtils.setField(productInquiryRequest, "title", "문의 제목");
        ReflectionTestUtils.setField(productInquiryRequest, "content", "문의 내용");
        ReflectionTestUtils.setField(productInquiryRequest, "isSecret", true);

        String content = objectMapper.writeValueAsString(productInquiryRequest);

        willDoNothing().given(productInquiryPostService)
                       .createProductInquiry(any(MemberInfo.class), any(ProductInquiryRequest.class), anyLong());

        this.mockMvc.perform(post("/products/" + 1L + "/inquiry")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                    .andExpect(status().isCreated());
        then(productInquiryPostService).should(times(1))
                                       .createProductInquiry(any(MemberInfo.class), any(ProductInquiryRequest.class),
                                           anyLong());
    }

    @Test
    @DisplayName("상품에 대한 전체 문의 조회 테스트")
    void testRetrieveProductInquiryByProductId() throws Exception {
        given(productInquiryPostService.retrieveProductInquiryByProductId(anyLong(), any(PageRequest.class)))
            .willReturn(responses);

        this.mockMvc.perform(get("/products/" + 1L + "/inquiries")
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        then(productInquiryPostService).should(times(1))
                                       .retrieveProductInquiryByProductId(anyLong(), any(PageRequest.class));
    }

    @Test
    @DisplayName("상품 문의 삭제 테스트")
    void testDeleteProductInquiry() throws Exception {
        willDoNothing().given(productInquiryPostService)
                       .deleteProductInquiry(anyLong(), anyLong());

        this.mockMvc.perform(delete("/products/" + 1L + "/inquiry/" + 1L)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());

        then(productInquiryPostService).should(times(1)).deleteProductInquiry(anyLong(), anyLong());
    }

}
