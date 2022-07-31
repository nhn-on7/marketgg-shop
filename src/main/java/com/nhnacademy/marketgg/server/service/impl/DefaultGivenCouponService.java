package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.constant.CouponState;
import com.nhnacademy.marketgg.server.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import com.nhnacademy.marketgg.server.service.GivenCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.nhnacademy.marketgg.server.dto.response.CouponState.EXPIRED;
import static com.nhnacademy.marketgg.server.dto.response.CouponState.USED;
import static com.nhnacademy.marketgg.server.dto.response.CouponState.VALID;

@Service
@RequiredArgsConstructor
public class DefaultGivenCouponService implements GivenCouponService {

    private final GivenCouponRepository givenCouponRepository;
    private final CouponRepository couponRepository;
    private final UsedCouponRepository usedCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createGivenCoupons(final String uuid,
                                   @Valid final GivenCouponCreateRequest givenCouponRequest) {

        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);
        Coupon coupon = couponRepository.findById(givenCouponRequest.getCouponId())
                                        .orElseThrow(CouponNotFoundException::new);

        GivenCoupon givenCoupon = new GivenCoupon(coupon, member, givenCouponRequest);
        givenCouponRepository.save(givenCoupon);
    }

    @Override
    public List<GivenCouponResponse> retrieveGivenCoupons(final String uuid, final Pageable pageable) {
        List<GivenCoupon> givenCoupons = givenCouponRepository.findAllByMember_Uuid(uuid, pageable).getContent();
        return givenCoupons.stream()
                           .map(this::checkAvailability)
                           .collect(Collectors.toUnmodifiableList());
    }

    private GivenCouponResponse checkAvailability(final GivenCoupon givenCoupons) {

        CouponState state;
        Integer expiredDate = givenCoupons.getCoupon().getExpiredDate();
        LocalDateTime expirationPeriod = givenCoupons.getCreatedAt().plusDays(expiredDate);

        if (!usedCouponRepository.findAllByGivenCoupon(givenCoupons).isEmpty()) {
            state = USED;
        } else if (expirationPeriod.isBefore(LocalDateTime.now())) {
            state = EXPIRED;
        } else {
            state = VALID;
        }
        return new GivenCouponResponse(givenCoupons, state, expirationPeriod);
    }

}
