package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.controller.admin.AdminProductInquiryPostController;
import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.server.service.product.ProductInquiryPostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminProductInquiryPostController.class)
class AdminProductInquiryPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductInquiryPostService productInquiryPostService;

    @Test
    @DisplayName("상품 문의 답글 등록 테스트")
    void testUpdateProductInquiryReply() throws Exception {
        ProductInquiryRequest productInquiryRequest = new ProductInquiryRequest();
        String content = objectMapper.writeValueAsString(productInquiryRequest);

        doNothing().when(productInquiryPostService)
                   .updateProductInquiryReply(any(ProductInquiryRequest.class), anyLong(), anyLong());

        this.mockMvc.perform(put("/admin/products/" + 1L + "/inquiries/" + 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                    .andExpect(status().isOk());
        verify(productInquiryPostService, times(1))
                .updateProductInquiryReply(any(ProductInquiryRequest.class), anyLong(), anyLong());
    }
}