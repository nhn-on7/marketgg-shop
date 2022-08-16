package com.nhnacademy.marketgg.server.controller.payment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentVerifyRequest;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.service.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(PaymentController.class)
@ActiveProfiles({ "devdb" })
class PaymentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PaymentService paymentService;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                      .alwaysDo(print())
                                      .build();
    }

    @Test
    @DisplayName("결제 검증 요청")
    void testVerifyRequest() throws Exception {
        BDDMockito.given(paymentService.verifyRequest(any(PaymentVerifyRequest.class)))
                  .willReturn(any(PaymentResponse.class));

        mockMvc.perform(post("/payments/verify")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(Dummy.getPaymentVerifyRequest())))
               .andExpect(status().isOk());

        then(paymentService).should(times(1))
                            .verifyRequest(any(PaymentVerifyRequest.class));
    }

    @Test
    @DisplayName("결제 승인 요청")
    void testConfirmPayment() throws Exception {
        willDoNothing().given(paymentService).pay(any(PaymentRequest.class));

        mockMvc.perform(post("/payments/confirm")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(Dummy.getPaymentRequest())))
               .andExpect(status().isCreated());

        then(paymentService).should(times(1))
                            .pay(any(PaymentRequest.class));
    }

    @Test
    @DisplayName("결제 취소 요청")
    void testCancelPayment() throws Exception {
        willDoNothing().given(paymentService).cancelPayment(anyString(), any(PaymentCancelRequest.class));

        this.mockMvc.perform(post("/payments/" + Dummy.getPaymentRequest().getPaymentKey() + "/cancel")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(Dummy.getPaymentCancelRequest())))
                    .andExpect(status().isOk());

        then(paymentService).should(times(1))
                            .cancelPayment(anyString(), any(PaymentCancelRequest.class));
    }

}
