package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.service.CouponService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultCouponService implements CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    @Override
    public void createCoupon(@Valid final CouponDto couponDto) {
        Coupon coupon = new Coupon(couponDto);
        couponRepository.save(coupon);
    }

    @Override
    public CouponDto retrieveCoupon(final Long id) {

        return couponRepository.findCouponDtoById(id).orElseThrow(CouponNotFoundException::new);
    }

    @Override
    public List<CouponDto> retrieveCoupons(final Pageable pageable) {

        return couponRepository.findAllCoupons(pageable).getContent();
    }

    @Transactional
    @Override
    public void updateCoupon(final Long couponId, @Valid final CouponDto couponDto) {
        Coupon coupon = couponRepository.findById(couponId)
                                        .orElseThrow(CouponNotFoundException::new);
        coupon.updateCoupon(couponDto);

        couponRepository.save(coupon);
    }

    @Transactional
    @Override
    public void deleteCoupon(final Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                                        .orElseThrow(CouponNotFoundException::new);

        couponRepository.delete(coupon);
    }

}
