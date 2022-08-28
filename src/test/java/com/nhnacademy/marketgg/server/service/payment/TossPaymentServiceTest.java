package com.nhnacademy.marketgg.server.service.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.constant.payment.PaymentType;
import com.nhnacademy.marketgg.server.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentConfirmRequest;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.dummy.Dummy;
import com.nhnacademy.marketgg.server.dummy.PaymentDummy;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.payment.Payment;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.payment.CardPaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.PaymentAdapter;
import com.nhnacademy.marketgg.server.repository.payment.PaymentRepository;
import com.nhnacademy.marketgg.server.service.order.DefaultOrderService;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@Transactional
class TossPaymentServiceTest {

    @InjectMocks
    TossPaymentService paymentService;

    @Mock
    DefaultOrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    PaymentAdapter paymentAdapter;

    @Mock
    ObjectMapper objectMapper;

    @Test
    @DisplayName("결제 요청 검증")
    void testVerifyRequest() {
        Long orderId = 1L;
        BDDMockito.given(orderService.detachPrefix(anyString())).willReturn(orderId);

        Order spyOrder = spy(Dummy.getDummyOrder());
        BDDMockito.given(orderRepository.findById(orderId)).willReturn(Optional.of(spyOrder));

        OrderToPayment dummyOrderToPayment = PaymentDummy.getOrderToPayment();
        BDDMockito.given(spyOrder.getTotalAmount()).willReturn(dummyOrderToPayment.getTotalAmount());

        BooleanSupplier supplier = paymentService.verifyRequest(dummyOrderToPayment);

        assertThat(supplier.getAsBoolean()).isTrue();
    }

    @Test
    @DisplayName("결제 수단에 따른 결제 승인")
    void testPay() throws JsonProcessingException {
        PaymentResponse spyPaymentResponse = spy(PaymentDummy.createPaymentResponse(PaymentType.CARD.getName()));
        BDDMockito.doReturn(spyPaymentResponse).when(objectMapper).readValue(anyString(), eq(PaymentResponse.class));

        BDDMockito.given(objectMapper.writeValueAsString(spyPaymentResponse))
                  .willReturn(PaymentDummy.getPaymentResponse(PaymentDummy.cardResult()));

        String spyString = objectMapper.writeValueAsString(spyPaymentResponse);
        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(spyString));

        BDDMockito.given(paymentAdapter.confirm(any(PaymentConfirmRequest.class))).willReturn(response);
        BDDMockito.given(response.getBody()).willReturn(spyString);

        PaymentConfirmRequest dummyPaymentRequest = spy(PaymentDummy.getPaymentConfirmRequest());

        Order spyOrder = spy(Dummy.getDummyOrder());
        BDDMockito.given(orderRepository.findById(anyLong())).willReturn(Optional.of(spyOrder));
        BDDMockito.given(paymentRepository.save(any(Payment.class))).willReturn(any(Payment.class));

        PaymentResponse paymentResponse = paymentService.pay(dummyPaymentRequest);

        assertThat(paymentResponse).isNotNull();

        verify(objectMapper, times(1)).readValue(anyString(), eq(PaymentResponse.class));
    }

    @Test
    @DisplayName("결제 승인 도중 파싱 오류 시 예외 처리 여부")
    void testPayInTheMiddleOfThrowException() throws JsonProcessingException {
        BDDMockito.doThrow(JsonProcessingException.class).when(objectMapper).readValue(anyString(), eq(PaymentResponse.class));

        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(""));

        BDDMockito.given(paymentAdapter.confirm(any(PaymentConfirmRequest.class))).willReturn(response);
        BDDMockito.given(response.getBody()).willReturn("");

        PaymentConfirmRequest dummyPaymentRequest = spy(PaymentDummy.getPaymentConfirmRequest());

        assertThatThrownBy(() -> paymentService.pay(dummyPaymentRequest)).isInstanceOf(UncheckedIOException.class);
    }

    @Test
    @DisplayName("결제된 건에 대하여 결제 조회")
    void testRetrievePayment() throws JsonProcessingException {
        PaymentResponse spyPaymentResponse = spy(PaymentDummy.createPaymentResponse(PaymentType.CARD.getName()));
        BDDMockito.doReturn(spyPaymentResponse).when(objectMapper).readValue(anyString(), eq(PaymentResponse.class));

        BDDMockito.given(objectMapper.writeValueAsString(spyPaymentResponse))
                  .willReturn(PaymentDummy.getPaymentResponse(PaymentDummy.cardResult()));

        String spyString = objectMapper.writeValueAsString(spyPaymentResponse);
        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(spyString));

        BDDMockito.given(paymentAdapter.retrievePayment(anyString())).willReturn(response);
        BDDMockito.given(response.getBody()).willReturn(spyString);

        PaymentResponse paymentResponse = paymentService.retrievePayment(PaymentDummy.PAYMENT_KEY);

        assertThat(paymentResponse).isNotNull();

        verify(objectMapper, times(1)).readValue(anyString(), eq(PaymentResponse.class));
    }

    @Test
    @DisplayName("결제 승인 도중 파싱 오류 시 예외 처리 여부")
    void testRetrievePaymentInTheMiddleOfThrowException() throws JsonProcessingException {
        BDDMockito.doThrow(JsonProcessingException.class).when(objectMapper).readValue(anyString(), eq(PaymentResponse.class));

        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(""));

        BDDMockito.given(paymentAdapter.retrievePayment(anyString())).willReturn(response);
        BDDMockito.given(response.getBody()).willReturn("");

        assertThatThrownBy(() -> paymentService.retrievePayment(PaymentDummy.PAYMENT_KEY)).isInstanceOf(UncheckedIOException.class);
    }

//     @Test
//     void createVirtualAccounts() {
//     }
//
//     @Test
//     void putMoneyInVirtualAccount() {
//     }

    @Test
    void cancelPayment() throws JsonProcessingException {
        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(""));
        BDDMockito.given(paymentAdapter.cancel(anyString(), any(PaymentCancelRequest.class))).willReturn(response);

        Payment payment = PaymentDummy.createPayment();
        BDDMockito.given(paymentRepository.findByPaymentKey(anyString())).willReturn(Optional.of(payment));

        paymentService.cancelPayment(PaymentDummy.PAYMENT_KEY, PaymentDummy.getPaymentCancelRequest());
    }

}
