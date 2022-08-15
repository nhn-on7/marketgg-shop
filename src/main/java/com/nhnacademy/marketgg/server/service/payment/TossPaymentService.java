package com.nhnacademy.marketgg.server.service.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentVerifyRequest;
import com.nhnacademy.marketgg.server.dto.request.member.MemberCreateRequest;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.payment.CardPayment;
import com.nhnacademy.marketgg.server.entity.payment.MobilePhonePayment;
import com.nhnacademy.marketgg.server.entity.payment.Payment;
import com.nhnacademy.marketgg.server.entity.payment.TransferPayment;
import com.nhnacademy.marketgg.server.entity.payment.VirtualAccountPayment;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.payment.CardPaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.MobilePhonePaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.PaymentAdapter;
import com.nhnacademy.marketgg.server.repository.payment.PaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.TransferPaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.VirtualAccountPaymentRepository;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class TossPaymentService implements PaymentService {

    private final PaymentAdapter paymentAdapter;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final CardPaymentRepository cardPaymentRepository;
    private final VirtualAccountPaymentRepository virtualAccountPaymentRepository;
    private final TransferPaymentRepository transferPaymentRepository;
    private final MobilePhonePaymentRepository mobilePhonePaymentRepository;

    @Override
    public PaymentResponse verifyRequest(final PaymentVerifyRequest paymentRequest) {
        return new PaymentResponse();
    }

    /**
     * 최종 결제 승인을 처리합니다.
     *
     * @param paymentRequest - 결제 요청 정보
     * @return PaymentResponse
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void pay(final PaymentRequest paymentRequest) {
        ResponseEntity<String> response = paymentAdapter.confirm(paymentRequest);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        PaymentResponse paymentResponse;
        try {
            paymentResponse = objectMapper.readValue(response.getBody(), PaymentResponse.class);
        } catch (JsonProcessingException ex) {
            throw new UncheckedIOException(ex);
        }

        Order order = orderRepository.findById(1L)
                                     .orElseThrow();

        Payment payment = this.toEntity(order, paymentResponse);
        Payment savedPayment = paymentRepository.save(payment);

        if (Objects.nonNull(paymentResponse.getCard())) {
            CardPayment cardPayment = this.toEntity(savedPayment, paymentResponse.getCard());
            cardPaymentRepository.save(cardPayment);
        }

        if (Objects.nonNull(paymentResponse.getVirtualAccount())) {
            VirtualAccountPayment virtualAccountPayment = this.toEntity(paymentResponse.getVirtualAccount());
            virtualAccountPaymentRepository.save(virtualAccountPayment);
        }

        if (Objects.nonNull(paymentResponse.getTransfer())) {
            TransferPayment transferPayment = this.toEntity(paymentResponse.getTransfer());
            transferPaymentRepository.save(transferPayment);
        }

        if (Objects.nonNull(paymentResponse.getMobilePhone())) {
            MobilePhonePayment mobilePhonePayment = this.toEntity(paymentResponse.getMobilePhone());
            mobilePhonePaymentRepository.save(mobilePhonePayment);
        }
    }

    @Override
    public PaymentResponse cancelPayment(Long paymentKey, PaymentCancelRequest paymentRequest) {
        return null;
    }

}
