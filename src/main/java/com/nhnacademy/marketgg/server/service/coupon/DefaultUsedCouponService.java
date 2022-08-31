package com.nhnacademy.marketgg.server.service.coupon;

import com.nhnacademy.marketgg.server.dto.request.coupon.UsedCouponDto;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.nhnacademy.marketgg.server.exception.order.OrderNotFoundException;
import com.nhnacademy.marketgg.server.exception.usedcoupon.UsedCouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용 쿠폰 관리 서비스 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultUsedCouponService implements UsedCouponService {

    private final UsedCouponRepository usedCouponRepository;
    private final OrderRepository orderRepository;
    private final GivenCouponRepository givenCouponRepository;

    /**
     * {@inheritDoc}
     *
     * @param usedCouponDto - 사용 쿠폰 생성을 위한 DTO 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    @Transactional
    public void createUsedCoupons(@Valid final UsedCouponDto usedCouponDto) {
        Order order = orderRepository.findById(usedCouponDto.getOrderId()).orElseThrow(OrderNotFoundException::new);
        GivenCoupon givenCoupon = givenCouponRepository.findById(new GivenCoupon.Pk(usedCouponDto.getCouponId(),
                                                                                    usedCouponDto.getMemberId()))
                                                       .orElseThrow(UsedCouponNotFoundException
                                                                            .GivenCouponInMemberNotFoundException::new);

        UsedCoupon usedCoupon = this.toEntity(usedCouponDto, order, givenCoupon);
        usedCouponRepository.save(usedCoupon);
    }

    /**
     * {@inheritDoc}
     *
     * @param usedCouponDto - 삭제할 사용 쿠폰의 DTO 입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    @Transactional
    public void deleteUsedCoupons(@Valid final UsedCouponDto usedCouponDto) {
        UsedCoupon usedCoupon = usedCouponRepository.findById(new UsedCoupon.Pk(usedCouponDto.getOrderId(),
                                                                                usedCouponDto.getCouponId(),
                                                                                usedCouponDto.getMemberId()))
                                                    .orElseThrow(UsedCouponNotFoundException::new);

        usedCouponRepository.delete(usedCoupon);
    }
}
