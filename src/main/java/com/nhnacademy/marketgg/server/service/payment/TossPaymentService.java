package com.nhnacademy.marketgg.server.service.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.constant.payment.PaymentStatus;
import com.nhnacademy.marketgg.server.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentConfirmRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountCreateRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountDepositRequest;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.payment.CardPayment;
import com.nhnacademy.marketgg.server.entity.payment.MobilePhonePayment;
import com.nhnacademy.marketgg.server.entity.payment.Payment;
import com.nhnacademy.marketgg.server.entity.payment.TransferPayment;
import com.nhnacademy.marketgg.server.entity.payment.VirtualAccountPayment;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.exception.payment.PaymentNotFoundException;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.payment.CardPaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.MobilePhonePaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.PaymentAdapter;
import com.nhnacademy.marketgg.server.repository.payment.PaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.TransferPaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.VirtualAccountPaymentRepository;
import com.nhnacademy.marketgg.server.service.order.OrderService;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 토스 결제대행사를 통한 결제 기능을 수행하기 위한 서비스 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class TossPaymentService implements PaymentService {

    private final PaymentAdapter paymentAdapter;
    private final ObjectMapper objectMapper;

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;
    private final CardPaymentRepository cardPaymentRepository;
    private final VirtualAccountPaymentRepository virtualAccountPaymentRepository;
    private final TransferPaymentRepository transferPaymentRepository;
    private final MobilePhonePaymentRepository mobilePhonePaymentRepository;

    /**
     * {@inheritDoc}
     * Market GG 주문 저장소에 등록된 금액과 결제 요청 데이터에 포함되어 있는 최종 결제 요청 금액이 일치할 경우 성공적으로 검증을 수행합니다.
     *
     * @param paymentRequest - 결제 검증 요청 데이터
     * @return 최종 결제 금액 일치 여부
     */
    @Override
    public BooleanSupplier verifyRequest(final OrderToPayment paymentRequest) {
        Long orderId = orderService.detachPrefix(paymentRequest.getOrderId());
        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(OrderNotFoundException::new);

        return () -> Objects.equals(order.getTotalAmount(), paymentRequest.getTotalAmount());
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentRequest - 결제 요청 정보
     * @return 결제 요청 데이터에 대한 응답 결과 데이터를 포함한 {@link PaymentResponse}
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public PaymentResponse pay(final PaymentConfirmRequest paymentRequest) {
        ResponseEntity<String> response = paymentAdapter.confirm(paymentRequest);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        PaymentResponse paymentResponse;
        try {
            paymentResponse = objectMapper.readValue(response.getBody(), PaymentResponse.class);
        } catch (JsonProcessingException ex) {
            throw new UncheckedIOException(ex);
        }

        Order order = orderRepository.findById(orderService.detachPrefix(paymentRequest.getOrderId())).orElseThrow();

        Payment payment = this.toEntity(order, paymentResponse);
        Payment savedPayment = paymentRepository.save(payment);

        if (Objects.nonNull(paymentResponse.getCard())) {
            CardPayment cardPayment = this.toEntity(savedPayment, paymentResponse.getCard());
            cardPaymentRepository.save(cardPayment);
        } else if (Objects.nonNull(paymentResponse.getVirtualAccount())) {
            VirtualAccountPayment virtualAccountPayment = this.toEntity(savedPayment,
                                                                        paymentResponse.getVirtualAccount());
            virtualAccountPaymentRepository.save(virtualAccountPayment);
        } else if (Objects.nonNull(paymentResponse.getTransfer())) {
            TransferPayment transferPayment = this.toEntity(payment, paymentResponse.getTransfer());
            transferPaymentRepository.save(transferPayment);
        } else if (Objects.nonNull(paymentResponse.getMobilePhone())) {
            MobilePhonePayment mobilePhonePayment = this.toEntity(payment, paymentResponse.getMobilePhone());
            mobilePhonePaymentRepository.save(mobilePhonePayment);
        }

        return paymentResponse;
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentKey - 결제 건에 대한 고유 키 값
     * @return 결제한 데이터에 대한 응답 결과 데이터를 포함한 {@link PaymentResponse}
     */
    @Override
    public PaymentResponse retrievePayment(String paymentKey) {
        ResponseEntity<String> response = paymentAdapter.retrievePayment(paymentKey);

        PaymentResponse paymentResponse;
        try {
            paymentResponse = objectMapper.readValue(response.getBody(), PaymentResponse.class);
        } catch (JsonProcessingException ex) {
            throw new UncheckedIOException(ex);
        }

        return paymentResponse;
    }

    /**
     * {@inheritDoc}
     *
     * @param virtualAccountRequest - 가상계좌 발급을 위한 요청 데이터가 담겨있는 객체
     * @return 결제 요청 데이터에 대한 응답 결과 데이터를 포함한 {@link PaymentResponse}
     */
    @Override
    public void createVirtualAccounts(VirtualAccountCreateRequest virtualAccountRequest) {
    }

    /**
     * {@inheritDoc}
     *
     * @param virtualAccountRequest - 가상계좌 입금 요청 데이터
     */
    @Override
    public void putMoneyInVirtualAccount(VirtualAccountDepositRequest virtualAccountRequest) {
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentKey     - 결제 건에 대한 고유 키 값
     * @param paymentRequest - 결제 취소 요청 데이터
     */
    @Override
    public void cancelPayment(final String paymentKey, final PaymentCancelRequest paymentRequest) {
        paymentAdapter.cancel(paymentKey, paymentRequest);

        Payment foundPayment = paymentRepository.findByPaymentKey(paymentKey)
                                                .orElseThrow(PaymentNotFoundException::new);

        foundPayment.changePaymentStatus(PaymentStatus.CANCELED);
    }

}
