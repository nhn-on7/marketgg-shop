package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.UsedCouponDto;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.UsedCoupon;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultUsedCouponServiceTest {

    @InjectMocks
    DefaultUsedCouponService usedCouponService;

    @Mock
    UsedCouponRepository usedCouponRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    GivenCouponRepository givenCouponRepository;

    UsedCouponDto usedCouponDto;
    Order order;
    GivenCoupon givenCoupon;
    UsedCoupon usedCoupon;

    @BeforeEach
    void setUp() {
        order = Order.test();
        givenCoupon = GivenCoupon.test();
        usedCouponDto = new UsedCouponDto();
        usedCoupon = new UsedCoupon(usedCouponDto, order, givenCoupon);
    }


    @Test
    void testCreateUsedCouponsSuccess() {
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(order));
        when(givenCouponRepository.findById(any())).thenReturn(Optional.ofNullable(givenCoupon));

        usedCouponService.createUsedCoupons(usedCouponDto);

        verify(usedCouponRepository, times(1)).save(any(UsedCoupon.class));
    }

    @Test
    void deleteUsedCoupons() {
        when(usedCouponRepository.findById(any())).thenReturn(Optional.ofNullable(usedCoupon));

        usedCouponService.deleteUsedCoupons(usedCouponDto);

        verify(usedCouponRepository, times(1)).delete(any(UsedCoupon.class));
    }

}
