package com.nhnacademy.marketgg.server.eventlistener.handler;

import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderProductUpdatedEvent;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderProductUpdatedEventHandler {

    private final ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createUsedCoupon(OrderProductUpdatedEvent event) {
        List<Product> products = productRepository.findByIds(event.getProductIds());

        int i = 0;
        for (Product product : products) {
            long remain = product.getTotalStock() - event.getProductAmounts().get(i++);
            product.updateTotalStock(remain);
            productRepository.save(product);
        }
    }

}
