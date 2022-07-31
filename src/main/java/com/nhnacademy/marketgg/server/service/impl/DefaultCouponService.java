package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.mapper.impl.CouponMapper;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCouponService implements CouponService {

    // MEMO 6: Mapstruct 사용하기 위해 등록한 컴포넌트 빈을 주입
    private final CouponMapper couponMapper;
    private final CouponRepository couponRepository;

    // MEMO 7: Mapstruct 사용 (toEntity, toDto)
    @Transactional
    @Override
    public void createCoupon(final CouponDto couponDto) {
        Coupon coupon = couponMapper.toEntity(couponDto);
        couponRepository.save(coupon);
    }

    @Override
    public CouponDto retrieveCoupon(final Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);
        return couponMapper.toDto(coupon);
    }

    @Override
    public List<CouponDto> retrieveCoupons(final Pageable pageable) {
        List<Coupon> couponList = couponRepository.findAllCoupons(pageable).getContent();

        return couponList.stream()
                         .map(couponMapper::toDto)
                         .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    @Override
    public void updateCoupon(final Long couponId, final CouponDto couponDto) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);

        // MEMO 8: Update method Dto -> Entity 로 null 아닌 컬럼만 업데이트
        couponMapper.updateCouponFromCouponDto(couponDto, coupon);

        couponRepository.save(coupon);
    }

    @Transactional
    @Override
    public void deleteCoupon(final Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);

        couponRepository.delete(coupon);
    }

}
