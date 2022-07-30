package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminCustomerServicePostController.class)
public class AdminCustomerServiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerServicePostService customerServicePostService;

    private static final String DEFAULT_ADMIN_CUSTOMER_SERVICE = "/admin/customer-services";

    @Test
    @DisplayName("1:1 문의 단건 조회 - 관리자")
    void testRetrieveOtoInquiry() throws Exception {
        given(customerServicePostService.retrieveCustomerServicePost(anyLong())).willReturn(null);

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}",1L))
                    .andExpect(status().isOk());

        then(customerServicePostService).should().retrieveCustomerServicePost(anyLong());
    }

    @Test
    @DisplayName("1:1 문의 목록 조회 - 관리자")
    void testRetrieveOtoInquiries() throws Exception {
        given(customerServicePostService.retrieveOtoInquiries(any(Pageable.class))).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries"))
                    .andExpect(status().isOk());

        then(customerServicePostService).should().retrieveOtoInquiries(any(Pageable.class));
    }

    @Test
    @DisplayName("1:1 문의 삭제 - 관리자")
    void testDeleteOtoInquiries() throws Exception {
        willDoNothing().given(customerServicePostService).deleteCustomerServicePost(anyLong());

        this.mockMvc.perform(delete(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries/{inquiryId}", 1L))
                    .andExpect(status().isOk());

        then(customerServicePostService).should().deleteCustomerServicePost(anyLong());
    }

    @Test
    @DisplayName("고객센터 게시글 사유 목록 조회")
    void testRetrieveAllReasonValues() throws Exception {
        this.mockMvc.perform(get(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/reasons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(9)));
    }

}
