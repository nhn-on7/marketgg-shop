package com.nhnacademy.marketgg.server.service.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.constant.OrderStatus;
import com.nhnacademy.marketgg.server.constant.payment.PaymentStatus;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentCancelRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.PaymentConfirmRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountCreateRequest;
import com.nhnacademy.marketgg.server.dto.payment.request.VirtualAccountDepositRequest;
import com.nhnacademy.marketgg.server.dto.payment.response.PaymentResponse;
import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.dto.request.point.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.order.OrderToPayment;
import com.nhnacademy.marketgg.server.entity.CartProduct;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.nhnacademy.marketgg.server.entity.payment.CardPayment;
import com.nhnacademy.marketgg.server.entity.payment.MobilePhonePayment;
import com.nhnacademy.marketgg.server.entity.payment.Payment;
import com.nhnacademy.marketgg.server.entity.payment.TransferPayment;
import com.nhnacademy.marketgg.server.entity.payment.VirtualAccountPayment;
import com.nhnacademy.marketgg.server.exception.givencoupon.GivenCouponNotFoundException;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.exception.payment.PaymentNotFoundException;
import com.nhnacademy.marketgg.server.repository.cart.CartProductRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.orderproduct.OrderProductRepository;
import com.nhnacademy.marketgg.server.repository.payment.CardPaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.MobilePhonePaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.PaymentAdapter;
import com.nhnacademy.marketgg.server.repository.payment.PaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.TransferPaymentRepository;
import com.nhnacademy.marketgg.server.repository.payment.VirtualAccountPaymentRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import com.nhnacademy.marketgg.server.service.order.OrderService;
import com.nhnacademy.marketgg.server.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static java.util.stream.Collectors.toList;

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
    private final OrderProductRepository orderProductRepository;
    private final UsedCouponRepository usedCouponRepository;
    private final GivenCouponRepository givenCouponRepository;
    private final PointService pointService;
    private final ProductRepository productRepository;
    private final CartProductRepository cartProductRepository;

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

        this.confirmOrder(order, paymentRequest);
        order.updateStatus(OrderStatus.PAY_COMPLETE.getStatus());

        return paymentResponse;
    }

    /**
     * 결제 승인 확정 후 주문에 대한 변동사항을 확정 처리합니다.
     *
     * @param order - 결제완료 한 주문
     * @param paymentRequest - 결제 승인 확정 요청 정보
     */
    private void confirmOrder(Order order, PaymentConfirmRequest paymentRequest) {
        Long memberId = order.getMember().getId();
        Optional<Long> coupon = paymentRequest.getCouponId();
        Integer usedPoint = paymentRequest.getUsedPoint();
        List<Long> productIds = paymentRequest.getOrderedProducts();
        List<Integer> productAmounts = orderProductRepository.findByOrderId(order.getId())
                                                             .stream().map(ProductToOrder::getAmount).collect(toList());

        this.discountConfirm(memberId, order, coupon, usedPoint);
        this.orderedProductConfirm(productIds, productAmounts);
        this.cartConfirm(order, productIds);
    }

    /**
     * 할인 적용 부분에 대한 확정 처리입니다.
     *
     * @param memberId - 주문자 Id
     * @param order - 결제완료 한 주문
     * @param coupon - 주문에 사용한 쿠폰
     * @param usedPoint - 주문에 사용한 포인트 금액
     */
    private void discountConfirm(Long memberId, Order order, Optional<Long> coupon, Integer usedPoint) {
        if (coupon.isPresent()) {
            Long couponId = coupon.get();
            GivenCoupon givenCoupon = givenCouponRepository.findById(new GivenCoupon.Pk(couponId, memberId))
                                                           .orElseThrow(GivenCouponNotFoundException::new);
            UsedCoupon usedCoupon = UsedCoupon.builder()
                                              .pk(new UsedCoupon.Pk(order.getId(), couponId, memberId))
                                              .order(order)
                                              .givenCoupon(givenCoupon)
                                              .build();
            usedCouponRepository.save(usedCoupon);
        }
        pointService.createPointHistoryForOrder(memberId, order.getId(), new PointHistoryRequest(-usedPoint, "포인트 사용"));
    }

    /**
     * 주문한 상품 재고량 변동 확정 처리입니다.
     *
     * @param productIds - 주문한 상품 id 목록
     * @param productAmounts - 주문한 상품 수량 목록
     */
    private void orderedProductConfirm(List<Long> productIds, List<Integer> productAmounts) {
        List<Product> products = productRepository.findByIds(productIds);

        int i = 0;
        for (Product product : products) {
            long remain = product.getTotalStock() - productAmounts.get(i++);
            product.updateTotalStock(remain);
            productRepository.save(product);
        }
    }

    /**
     * 장바구니에서 주문한 상품 삭제 확정 처리입니다.
     *
     * @param order - 결제완료한 주문
     * @param productIds - 주문한 상품 Id 목록
     */
    private void cartConfirm(Order order, List<Long> productIds) {
        List<CartProduct.Pk> cartProductPk = productIds.stream()
                                                       .map(productId -> new CartProduct.Pk(
                                                               order.getMember().getCart().getId(),
                                                               productId))
                                                       .collect(toList());
        cartProductRepository.deleteAllById(cartProductPk);
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
        Order order = orderRepository.findById(foundPayment.getOrder().getId())
                                     .orElseThrow(OrderNotFoundException::new);

        foundPayment.changePaymentStatus(PaymentStatus.CANCELED);
        order.updateStatus(OrderStatus.CANCEL_COMPLETE.getStatus());
    }

}
