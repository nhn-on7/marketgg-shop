package com.nhnacademy.marketgg.server.service.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.constant.payment.PaymentType;
import com.nhnacademy.marketgg.server.dto.payment.response.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentConfirmRequest;
import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.dummy.Dummy;
import com.nhnacademy.marketgg.dummy.PaymentDummy;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.payment.Payment;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderCartUpdatedEvent;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderPointSavedEvent;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderProductUpdatedEvent;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.orderproduct.OrderProductRepository;
import com.nhnacademy.marketgg.server.repository.payment.PaymentAdapter;
import com.nhnacademy.marketgg.server.repository.payment.PaymentRepository;
import com.nhnacademy.marketgg.server.service.order.DefaultOrderService;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
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
    ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);

    @Mock
    DefaultOrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderProductRepository orderProductRepository;

    @Mock
    PaymentAdapter paymentAdapter;

    @Mock
    ObjectMapper objectMapper;

    @Test
    @DisplayName("결제 요청 검증")
    void testVerifyRequest() {
        Long orderId = 1L;
        given(orderService.detachPrefix(anyString())).willReturn(orderId);

        Order spyOrder = spy(Dummy.getDummyOrder());
        given(orderRepository.findById(orderId)).willReturn(Optional.of(spyOrder));

        OrderToPayment dummyOrderToPayment = PaymentDummy.getOrderToPayment();
        given(spyOrder.getTotalAmount()).willReturn(dummyOrderToPayment.getTotalAmount());

        BooleanSupplier supplier = paymentService.verifyRequest(dummyOrderToPayment);

        assertThat(supplier.getAsBoolean()).isTrue();
    }

    @Test
    @DisplayName("결제 수단에 따른 결제 승인")
    void testPay() throws JsonProcessingException {
        PaymentResponse spyPaymentResponse = spy(PaymentDummy.createPaymentResponse(PaymentType.CARD.getType()));
        doReturn(spyPaymentResponse).when(objectMapper).readValue(anyString(), eq(PaymentResponse.class));

        given(objectMapper.writeValueAsString(spyPaymentResponse))
                  .willReturn(PaymentDummy.getPaymentResponse(PaymentDummy.cardResult()));

        String spyString = objectMapper.writeValueAsString(spyPaymentResponse);
        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(spyString));

        given(paymentAdapter.confirm(any(PaymentConfirmRequest.class))).willReturn(response);
        given(response.getBody()).willReturn(spyString);

        PaymentConfirmRequest dummyPaymentRequest = spy(PaymentDummy.getPaymentConfirmRequest());

        Order spyOrder = Dummy.getDummyOrder();
        given(orderService.detachPrefix(anyString())).willReturn(1L);
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(spyOrder));
        given(paymentRepository.save(any(Payment.class))).willReturn(Dummy.getDummyPayment());
        given(orderProductRepository.findByOrderId(anyLong())).willReturn(List.of(Dummy.getDummyProductToOrder()));

        PaymentResponse paymentResponse = paymentService.pay(dummyPaymentRequest);

        assertThat(paymentResponse).isNotNull();

        verify(objectMapper, times(1)).readValue(anyString(), eq(PaymentResponse.class));
        then(publisher).should(times(1)).publishEvent(any(OrderPointSavedEvent.class));
        then(publisher).should(times(1)).publishEvent(any(OrderProductUpdatedEvent.class));
        then(publisher).should(times(1)).publishEvent(any(OrderCartUpdatedEvent.class));
    }

    @Test
    @DisplayName("결제 승인 도중 파싱 오류 시 예외 처리 여부")
    void testPayInTheMiddleOfThrowException() throws JsonProcessingException {
        doThrow(JsonProcessingException.class)
                  .when(objectMapper)
                  .readValue(anyString(), eq(PaymentResponse.class));

        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(""));

        given(paymentAdapter.confirm(any(PaymentConfirmRequest.class))).willReturn(response);
        given(response.getBody()).willReturn("");

        PaymentConfirmRequest dummyPaymentRequest = spy(PaymentDummy.getPaymentConfirmRequest());

        assertThatThrownBy(() -> paymentService.pay(dummyPaymentRequest)).isInstanceOf(UncheckedIOException.class);
    }

    @Test
    @DisplayName("결제된 건에 대하여 결제 조회")
    void testRetrievePayment() throws JsonProcessingException {
        PaymentResponse spyPaymentResponse = spy(PaymentDummy.createPaymentResponse(PaymentType.CARD.getType()));
        doReturn(spyPaymentResponse).when(objectMapper).readValue(anyString(), eq(PaymentResponse.class));

        given(objectMapper.writeValueAsString(spyPaymentResponse))
                  .willReturn(PaymentDummy.getPaymentResponse(PaymentDummy.cardResult()));

        String spyString = objectMapper.writeValueAsString(spyPaymentResponse);
        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(spyString));

        given(paymentAdapter.retrievePayment(anyString())).willReturn(response);
        given(response.getBody()).willReturn(spyString);

        PaymentResponse paymentResponse = paymentService.retrievePayment(PaymentDummy.PAYMENT_KEY);

        assertThat(paymentResponse).isNotNull();

        verify(objectMapper, times(1)).readValue(anyString(), eq(PaymentResponse.class));
    }

    @Test
    @DisplayName("결제 승인 도중 파싱 오류 시 예외 처리 여부")
    void testRetrievePaymentInTheMiddleOfThrowException() throws JsonProcessingException {
        doThrow(JsonProcessingException.class)
                  .when(objectMapper)
                  .readValue(anyString(), eq(PaymentResponse.class));

        ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(""));

        given(paymentAdapter.retrievePayment(anyString())).willReturn(response);
        given(response.getBody()).willReturn("");

        assertThatThrownBy(() -> paymentService.retrievePayment(PaymentDummy.PAYMENT_KEY)).isInstanceOf(
            UncheckedIOException.class);
    }

    @Test
    @DisplayName("가상계좌 발급")
    void createVirtualAccounts() {
        paymentService.createVirtualAccounts(PaymentDummy.getVirtualAccountCreateRequest());
    }

    @Test
    @DisplayName("발급된 가상계좌 입금 확인")
    void putMoneyInVirtualAccount() {
        paymentService.putMoneyInVirtualAccount(PaymentDummy.getVirtualAccountDepositRequest());
    }

    // @Test
    // @DisplayName("승인된 결제 취소")
    // void cancelPayment() throws JsonProcessingException {
    //     ResponseEntity<String> response = spy(ResponseEntity.ok().contentType(APPLICATION_JSON).body(""));
    //     given(paymentAdapter.cancel(anyString(), any(PaymentCancelRequest.class))).willReturn(response);
    //
    //     Payment payment = PaymentDummy.createPayment();
    //     given(paymentRepository.findByPaymentKey(anyString())).willReturn(Optional.of(payment));
    //
    //     paymentService.cancelPayment(PaymentDummy.PAYMENT_KEY, PaymentDummy.getPaymentCancelRequest());
    // }

}
