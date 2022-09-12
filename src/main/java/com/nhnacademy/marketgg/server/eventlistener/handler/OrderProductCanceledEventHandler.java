package com.nhnacademy.marketgg.server.eventlistener.handler;

import com.nhnacademy.marketgg.server.dto.request.order.ProductToOrder;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderProductCanceledEvent;
import com.nhnacademy.marketgg.server.repository.orderproduct.OrderProductRepository;
import com.nhnacademy.marketgg.server.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderProductCanceledEventHandler {

    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void cancelProductStockUpdate(OrderProductCanceledEvent event) {
        Long orderId = event.getOrder().getId();
        List<ProductToOrder> orderProducts = orderProductRepository.findByOrderId(orderId);
        List<Product> products = productRepository.findByIds(orderProducts.stream()
                                                                          .map(ProductToOrder::getId)
                                                                          .collect(Collectors.toList()));
        List<Integer> productAmounts =
                orderProducts.stream().map(ProductToOrder::getAmount).collect(Collectors.toList());

        int i = 0;
        for (Product product : products) {
            long stock = product.getTotalStock() + productAmounts.get(i++);
            product.updateTotalStock(stock);
            productRepository.save(product);
        }
    }

}
