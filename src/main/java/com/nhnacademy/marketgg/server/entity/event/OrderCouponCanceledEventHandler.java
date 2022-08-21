package com.nhnacademy.marketgg.server.entity.event;

import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

/**
 * OrderCouponCanceledEvent 를 받아 처리하는 클래스입니다.
 *
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class OrderCouponCanceledEventHandler {

    private final UsedCouponRepository usedCouponRepository;

    /**
     * 주문에 사용된 쿠폰 번호가 있다면 사용쿠폰 테이블에서 삭제합니다.
     *
     * @param event - 이벤트를 처리할 때 필요한 정보를 담고 있습니다.
     * @since 1.0.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void deleteUsedCoupon(OrderCouponCanceledEvent event) {
        Long orderId = event.getOrder().getId();
        Optional<Long> couponId = usedCouponRepository.findByOrderId(orderId);

        couponId.ifPresent(id -> usedCouponRepository.deleteById(new UsedCoupon.Pk(orderId, id, event.getOrder()
                                                                                                     .getMember()
                                                                                                     .getId())));
    }

}
