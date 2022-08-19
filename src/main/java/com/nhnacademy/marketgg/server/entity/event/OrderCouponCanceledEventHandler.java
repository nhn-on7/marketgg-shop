package com.nhnacademy.marketgg.server.entity.event;

import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCouponCanceledEventHandler {

    private final UsedCouponRepository usedCouponRepository;

    @TransactionalEventListener
    public void deleteUsedCoupon(OrderCouponCanceledEvent event) {
        Long orderId = event.getOrder().getId();
        Optional<Long> couponId = usedCouponRepository.findByOrderId(orderId);

        couponId.ifPresent(id -> usedCouponRepository.deleteById(new UsedCoupon.Pk(orderId, id, event.getOrder()
                                                                                                     .getMember()
                                                                                                     .getId())));
    }

}
