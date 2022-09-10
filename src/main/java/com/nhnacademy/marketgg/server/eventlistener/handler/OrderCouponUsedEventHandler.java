package com.nhnacademy.marketgg.server.eventlistener.handler;

import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.nhnacademy.marketgg.server.eventlistener.event.order.OrderCouponUsedEvent;
import com.nhnacademy.marketgg.server.exception.givencoupon.GivenCouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderCouponUsedEventHandler {

    private final GivenCouponRepository givenCouponRepository;

    private final UsedCouponRepository usedCouponRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createUsedCoupon(OrderCouponUsedEvent event) {
        Long couponId = event.getCouponId();
        Order order = event.getOrder();
        Long memberId = order.getMember().getId();

        GivenCoupon givenCoupon = givenCouponRepository.findById(new GivenCoupon.Pk(couponId, memberId))
                                                       .orElseThrow(GivenCouponNotFoundException::new);
        UsedCoupon usedCoupon = UsedCoupon.builder()
                                          .pk(new UsedCoupon.Pk(order.getId(), couponId, memberId))
                                          .order(order)
                                          .givenCoupon(givenCoupon)
                                          .build();
        usedCouponRepository.save(usedCoupon);
    }

}
