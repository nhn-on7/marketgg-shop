package com.nhnacademy.marketgg.server.service.coupon;

import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 쿠폰 서비스입니다.
 *
 * @author 민아영
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultCouponService implements CouponService {

    private final CouponRepository couponRepository;

    /**
     * {@inheritDoc}
     *
     * @param couponDto - 쿠폰 등록에 필요한 정보를 담은 DTO 입니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void createCoupon(@Valid final CouponDto couponDto) {
        Coupon coupon = this.toEntity(couponDto);
        couponRepository.save(coupon);
    }

    /**
     * {@inheritDoc}
     *
     * @param id 조회할 쿠폰의 식별번호 입니다.
     * @return 조회한 쿠폰 Dto 를 반환합니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Override
    public CouponDto retrieveCoupon(final Long id) {

        return couponRepository.findCouponDtoById(id).orElseThrow(CouponNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 조회할 페이지 정보입니다.
     * @return 조회한 쿠폰 Dto 목록을 반환합니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Override
    public PageEntity<CouponDto> retrieveCoupons(final Pageable pageable) {
        Page<CouponDto> allCoupons = couponRepository.findAllCoupons(pageable);

        return new PageEntity<>(allCoupons.getNumber(), allCoupons.getSize(),
            allCoupons.getTotalPages(), allCoupons.getContent());
    }

    /**
     * {@inheritDoc}
     *
     * @param couponId  수정할 쿠폰의 식별번호입니다.
     * @param couponDto 쿠폰 수정에 필요한 정보를 담은 DTO 입니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void updateCoupon(final Long couponId, @Valid final CouponDto couponDto) {
        Coupon coupon = couponRepository.findById(couponId)
                                        .orElseThrow(CouponNotFoundException::new);
        coupon.updateCoupon(couponDto);

        couponRepository.save(coupon);
    }

    /**
     * {@inheritDoc}
     *
     * @param couponId 삭제할 쿠폰의 식별번호입니다.
     * @author 민아영
     * @author 김정민
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void deleteCoupon(final Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                                        .orElseThrow(CouponNotFoundException::new);

        couponRepository.delete(coupon);
    }

}
