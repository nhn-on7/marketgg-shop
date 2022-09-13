package com.nhnacademy.marketgg.server.eventlistener.handler;

import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.eventlistener.event.givencoupon.GiveCouponEvent;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * GiveCouponEvent 를 처리하는 Handler 입니다.
 *
 * @author 민아영
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class GiveCouponHandler {

    private final CouponRepository couponRepository;
    private final GivenCouponRepository givenCouponRepository;

    /**
     * 실제로 지급될 쿠폰을 생성하고 저장하는 EventListener 입니다.
     * GiveCouponEvent 이 발행되면 Listener 가 실행됩니다.
     *
     * @param couponRequest 지급될 쿠폰의 정보를 담고 있는 이벤트 객체입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createGivenCoupon(final GiveCouponEvent couponRequest) {

        Coupon signUpCoupon = couponRepository.findCouponByName(couponRequest.getCouponName())
                                              .orElseThrow(CouponNotFoundException::new);

        GivenCoupon givenCoupon
            = new GivenCoupon(new GivenCoupon.Pk(signUpCoupon.getId(),
                                                 couponRequest.getMember().getId()), signUpCoupon,
                              couponRequest.getMember(), LocalDateTime.now());

        givenCouponRepository.save(givenCoupon);
    }

}
