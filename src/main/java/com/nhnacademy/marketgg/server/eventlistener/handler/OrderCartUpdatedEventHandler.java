package com.nhnacademy.marketgg.server.eventlistener.handler;

import com.nhnacademy.marketgg.server.entity.CartProduct;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderCartUpdatedEvent;
import com.nhnacademy.marketgg.server.repository.cart.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class OrderCartUpdatedEventHandler {

    private final CartProductRepository cartProductRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createUsedCoupon(OrderCartUpdatedEvent event) {
        List<CartProduct.Pk> cartProductPk = event.getProductIds().stream()
                                                       .map(productId -> new CartProduct.Pk(
                                                               event.getOrder().getMember().getCart().getId(),
                                                               productId))
                                                       .collect(toList());
        cartProductRepository.deleteAllById(cartProductPk);
    }

}
