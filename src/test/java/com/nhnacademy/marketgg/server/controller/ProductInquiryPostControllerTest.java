package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.service.ProductInquiryPostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    @DisplayName("상품 문의 등록 테스트")
    void testCreateProductInquiry() throws Exception {
        ProductInquiryRequest productInquiryRequest = new ProductInquiryRequest();
        ReflectionTestUtils.setField(productInquiryRequest, "title", "문의 제목");
        ReflectionTestUtils.setField(productInquiryRequest, "content", "문의 내용");
        ReflectionTestUtils.setField(productInquiryRequest, "isSecret", true);

        String content = objectMapper.writeValueAsString(productInquiryRequest);

        doNothing().when(productInquiryPostService)
                   .createProductInquiry(any(MemberInfo.class), any(ProductInquiryRequest.class), anyLong());

        this.mockMvc.perform(post("/products/" + 1L + "/inquiries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isCreated());
        verify(productInquiryPostService, times(1))
                .createProductInquiry(any(MemberInfo.class), any(ProductInquiryRequest.class), anyLong());
    }

    @Test
    @DisplayName("상품에 대한 전체 문의 조회 테스트")
    void testRetrieveProductInquiryByProductId() throws Exception {
        given(productInquiryPostService.retrieveProductInquiryByProductId(anyLong(), any(PageRequest.class)))
                .willReturn(responses);

        this.mockMvc.perform(get("/products/" + 1L + "/inquiries")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        verify(productInquiryPostService, times(1))
                .retrieveProductInquiryByProductId(anyLong(), any(PageRequest.class));
    }

    @Test
    @DisplayName("회원이 작성한 전체 상품 문의 조회 테스트")
    void testRetrieveProductInquiryByMemberId() throws Exception {
        given(productInquiryPostService.retrieveProductInquiryByMemberId(any(MemberInfo.class), any(PageRequest.class)))
                .willReturn(responses);

        this.mockMvc.perform(get("/members/product-inquiries")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        verify(productInquiryPostService, times(1))
                .retrieveProductInquiryByMemberId(any(MemberInfo.class), any(PageRequest.class));
    }

    @Test
    @DisplayName("상품 문의 삭제 테스트")
    void testDeleteProductInquiry() throws Exception {
        doNothing().when(productInquiryPostService)
                   .deleteProductInquiry(anyLong(), anyLong());

        this.mockMvc.perform(delete("/products/" + 1L + "/inquiries/" + 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        verify(productInquiryPostService, times(1)).deleteProductInquiry(anyLong(), anyLong());
    }

}
