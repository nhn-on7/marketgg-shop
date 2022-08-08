package com.nhnacademy.marketgg.server.service.impl;

import static com.nhnacademy.marketgg.server.constant.CouponState.EXPIRED;
import static com.nhnacademy.marketgg.server.constant.CouponState.USED;
import static com.nhnacademy.marketgg.server.constant.CouponState.VALID;

import com.nhnacademy.marketgg.server.constant.CouponState;
import com.nhnacademy.marketgg.server.dto.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.event.SignedUpCouponEvent;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.exception.givencoupon.GivenCouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import com.nhnacademy.marketgg.server.service.GivenCouponService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 지급 쿠폰 로직을 처리하는 구현체입니다.
 * <p>
 * {@link com.nhnacademy.marketgg.server.service.GivenCouponService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGivenCouponService implements GivenCouponService {

    private final GivenCouponRepository givenCouponRepository;
    private final CouponRepository couponRepository;
    private final UsedCouponRepository usedCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createGivenCoupons(final MemberInfo memberInfo,
                                   @Valid final GivenCouponCreateRequest givenCouponRequest) {

        Member member = memberRepository.findById(memberInfo.getId())
                                        .orElseThrow(GivenCouponNotFoundException.MemberInfoNotFoundException::new);
        Coupon coupon = couponRepository.findCouponByName(givenCouponRequest.getName())
                                        .orElseThrow(GivenCouponNotFoundException.CouponInfoNotFoundException::new);

        givenCouponRepository.save(new GivenCoupon(coupon, member));
    }

    @Override
    public List<GivenCouponResponse> retrieveGivenCoupons(final MemberInfo memberInfo, final Pageable pageable) {
        List<GivenCoupon> givenCoupons
            = givenCouponRepository.findByMemberId(memberInfo.getId(), pageable)
                                   .orElseThrow(GivenCouponNotFoundException::new)
                                   .getContent();

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

        return new GivenCouponResponse(givenCoupons, state.state(), expirationPeriod);
    }

    /**
     * 회원가입을 하면 지급 쿠폰을 자동 생성해주는 EventListener 입니다.
     *
     * @param coupon - 회원에게 지급할 쿠폰의 정보입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Async
    @TransactionalEventListener
    public void createGivenCoupon(SignedUpCouponEvent coupon) {
        Coupon signUpCoupon = couponRepository.findCouponByName(coupon.getCouponName())
                                              .orElseThrow(CouponNotFoundException::new);
        GivenCoupon givenCoupon = new GivenCoupon(signUpCoupon, coupon.getMember());
        givenCouponRepository.save(givenCoupon);
    }

}
