package com.nhnacademy.marketgg.server.service.coupon;

import static com.nhnacademy.marketgg.server.constant.CouponState.EXPIRED;
import static com.nhnacademy.marketgg.server.constant.CouponState.USED;
import static com.nhnacademy.marketgg.server.constant.CouponState.VALID;

import com.nhnacademy.marketgg.server.constant.CouponState;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.coupon.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.event.GivenCouponEvent;
import com.nhnacademy.marketgg.server.exception.coupon.CouponNotFoundException;
import com.nhnacademy.marketgg.server.exception.givencoupon.GivenCouponNotFoundException;
import com.nhnacademy.marketgg.server.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.server.repository.givencoupon.GivenCouponRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.usedcoupon.UsedCouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createGivenCoupon(final GivenCouponEvent coupon) {
        Coupon signUpCoupon = couponRepository.findCouponByName(coupon.getCouponName())
                                              .orElseThrow(CouponNotFoundException::new);
        GivenCoupon givenCoupon = new GivenCoupon(signUpCoupon, coupon.getMember());
        givenCouponRepository.save(givenCoupon);
    }

}
