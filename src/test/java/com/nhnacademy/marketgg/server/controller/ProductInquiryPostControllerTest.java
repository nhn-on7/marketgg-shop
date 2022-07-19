package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.service.ProductInquiryPostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductInquiryPostController.class)
class ProductInquiryPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductInquiryPostService productInquiryPostService;

    private static final String DEFAULT_INQUIRY = "/shop/v1";

    @Test
    @DisplayName("상품 문의 등록 테스트")
    void testCreateProductInquiry() throws Exception {
        ProductInquiryRequest productInquiryRequest = new ProductInquiryRequest();
        String content = objectMapper.writeValueAsString(productInquiryRequest);

        doNothing().when(productInquiryPostService)
                   .createProductInquiry(any(ProductInquiryRequest.class), anyLong());

        this.mockMvc.perform(post(DEFAULT_INQUIRY + "/products/" + 1L + "/inquiries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isCreated());
        verify(productInquiryPostService, times(1))
                .createProductInquiry(any(ProductInquiryRequest.class), anyLong());
    }

    @Test
    @DisplayName("상품에 대한 전체 문의 조회 테스트")
    void testRetrieveProductInquiryByProductId() throws Exception {
        given(productInquiryPostService.retrieveProductInquiryByProductId(anyLong())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_INQUIRY + "/products/" + 1L + "/inquiries")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        verify(productInquiryPostService, times(1)).retrieveProductInquiryByProductId(anyLong());
    }

    @Test
    @DisplayName("회원이 작성한 전체 상품 문의 조회 테스트")
    void testRetrieveProductInquiryByMemberId() throws Exception {
        given(productInquiryPostService.retrieveProductInquiryByMemberId(anyLong())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_INQUIRY + "/members/" + 1L + "/product-inquiries")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        verify(productInquiryPostService, times(1)).retrieveProductInquiryByMemberId(anyLong());
    }

    @Test
    @DisplayName("상품 문의 삭제 테스트")
    void testDeleteProductInquiry() throws Exception {
        doNothing().when(productInquiryPostService)
                   .deleteProductInquiry(anyLong(), anyLong());

        this.mockMvc.perform(delete(DEFAULT_INQUIRY + "/products/" + 1L + "/inquiries/" + 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        verify(productInquiryPostService, times(1)).deleteProductInquiry(anyLong(), anyLong());
    }

}
