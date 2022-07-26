package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminCustomerServicePostController.class)
public class AdminCustomerServiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerServicePostService customerServicePostService;

    // @Test
    // @DisplayName("")

}
