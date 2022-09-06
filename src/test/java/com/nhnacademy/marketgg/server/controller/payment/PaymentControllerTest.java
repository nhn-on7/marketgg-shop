package com.nhnacademy.marketgg.server.controller.payment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.payment.response.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentConfirmRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountCreateRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountDepositRequest;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.dummy.PaymentDummy;
import com.nhnacademy.marketgg.server.service.payment.PaymentService;
import java.util.function.BooleanSupplier;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(PaymentController.class)
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
        BooleanSupplier supplier = spy(BooleanSupplier.class);
        given(paymentService.verifyRequest(any(OrderToPayment.class)))
                  .willReturn(supplier);
        given(supplier.getAsBoolean()).willReturn(true);

        mockMvc.perform(post("/payments/verify")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(PaymentDummy.getOrderToPayment())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", Matchers.equalTo(true)));

        then(paymentService).should(times(1))
                            .verifyRequest(any(OrderToPayment.class));
    }

    @Test
    @DisplayName("결제 승인 요청")
    void testConfirmPayment() throws Exception {
        PaymentResponse spy = spy(PaymentResponse.class);
        given(paymentService.pay(any(PaymentConfirmRequest.class)))
                  .willReturn(spy);

        // ObjectMapper 로 필드 접근 시 접근 지정자를 ANY 로 설정하면 private 에도 접근 가능
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockMvc.perform(post("/payments/confirm")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(PaymentDummy.getPaymentConfirmRequest())))
               .andExpect(status().isCreated());

        then(paymentService).should(times(1))
                            .pay(any(PaymentConfirmRequest.class));
    }

    @Test
    @DisplayName("가상계좌 발급 요청")
    void testCreateVirtualAccounts() throws Exception {
        willDoNothing().given(paymentService).createVirtualAccounts(any(VirtualAccountCreateRequest.class));

        // ObjectMapper 로 필드 접근 시 접근 지정자를 ANY 로 설정하면 private 에도 접근 가능
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockMvc.perform(post("/payments/virtual-accounts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(PaymentDummy.getVirtualAccountCreateRequest())))
               .andExpect(status().isCreated());

        then(paymentService).should(times(1))
                            .createVirtualAccounts(any(VirtualAccountCreateRequest.class));
    }

    @Test
    @DisplayName("발급한 가상계좌 입금 확인")
    void testPutMoneyInVirtualAccount() throws Exception {
        willDoNothing().given(paymentService).putMoneyInVirtualAccount(any(VirtualAccountDepositRequest.class));

        mockMvc.perform(post("/payments/virtual-accounts/deposit")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(PaymentDummy.getVirtualAccountDepositRequest())))
               .andExpect(status().isOk());

        then(paymentService).should(times(1))
                            .putMoneyInVirtualAccount(any(VirtualAccountDepositRequest.class));
    }

    @Test
    @DisplayName("결제 취소 요청")
    void testCancelPayment() throws Exception {
        willDoNothing().given(paymentService).cancelPayment(anyString(), any(PaymentCancelRequest.class));

        String paymentKey = PaymentDummy.getPaymentConfirmRequest().getPaymentKey();
        this.mockMvc.perform(post("/payments/" + paymentKey + "/cancel")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(PaymentDummy.getPaymentCancelRequest())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success", Matchers.equalTo(true)));

        then(paymentService).should(times(1))
                            .cancelPayment(anyString(), any(PaymentCancelRequest.class));
    }

}
