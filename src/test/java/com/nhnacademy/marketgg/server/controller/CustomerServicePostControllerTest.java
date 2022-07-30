package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.request.PostRequest;
import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostDto;
import com.nhnacademy.marketgg.server.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerServicePostController.class)
public class CustomerServicePostControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerServicePostService customerServicePostService;

    private static final String DEFAULT_CUSTOMER_SERVICE = "/customer-services";

    @Test
    @DisplayName("1:1 문의 등록")
    void testCreateOtoInquiry() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new PostRequest());

        willDoNothing().given(customerServicePostService)
                       .createOtoInquiry(anyLong(), any(PostRequest.class));

        this.mockMvc.perform(post(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/members/{memberId}", 1L)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(requestBody))
                    .andExpect(status().isCreated());

        then(customerServicePostService).should().createOtoInquiry(anyLong(), any(PostRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 단건 조회 - 사용자")
    void testRetrieveOtoInquiry() throws Exception {
        given(customerServicePostService.retrieveCustomerServicePost(anyLong())).willReturn(null);

        this.mockMvc.perform(get(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L))
                    .andExpect(status().isOk());

        then(customerServicePostService).should().retrieveCustomerServicePost(anyLong());
    }

    @Test
    @DisplayName("본인 1:1 문의 목록 조회 - 사용자")
    void testRetrieveOwnOtoInquiries() throws Exception {
        given(customerServicePostService.retrieveOwnOtoInquiries(any(Pageable.class), anyLong())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/members/{memberId}", 1L))
                    .andExpect(status().isOk());

        then(customerServicePostService).should().retrieveOwnOtoInquiries(any(Pageable.class), anyLong());
    }

    @Test
    @DisplayName("1:1 문의 삭제 - 사용자")
    void testDeleteOtoInquiries() throws Exception {
        willDoNothing().given(customerServicePostService).deleteCustomerServicePost(anyLong());

        this.mockMvc.perform(delete(DEFAULT_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L))
                    .andExpect(status().isOk());

        then(customerServicePostService).should().deleteCustomerServicePost(anyLong());
    }
}
