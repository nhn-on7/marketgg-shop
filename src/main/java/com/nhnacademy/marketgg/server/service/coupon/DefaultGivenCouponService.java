package com.nhnacademy.marketgg.server.service.coupon;

import static com.nhnacademy.marketgg.server.constant.CouponStatus.EXPIRED;
import static com.nhnacademy.marketgg.server.constant.CouponStatus.USED;
import static com.nhnacademy.marketgg.server.constant.CouponStatus.VALID;

import com.nhnacademy.marketgg.server.constant.CouponStatus;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.info.MemberInfo;
import com.nhnacademy.marketgg.server.dto.request.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.coupon.GivenCouponResponse;
import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.GivenCoupon;
import com.nhnacademy.marketgg.server.entity.Member;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 지급 쿠폰 관리 서비스입니다.
 *
 * @author 민아영
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultGivenCouponService implements GivenCouponService {

    private final GivenCouponRepository givenCouponRepository;
    private final CouponRepository couponRepository;
    private final UsedCouponRepository usedCouponRepository;
    private final MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     *
     * @param memberInfo         - 등록할 회원의 정보입니다.
     * @param givenCouponRequest - 쿠폰 번호를 가진 요청 DTO 입니다.
     */
    @Transactional
    @Override
    public void createGivenCoupons(final MemberInfo memberInfo,
                                   @Valid final GivenCouponCreateRequest givenCouponRequest) {

        Member member = memberRepository.findById(memberInfo.getId())
                                        .orElseThrow(GivenCouponNotFoundException.MemberInfoNotFoundException::new);
        Coupon coupon = couponRepository.findCouponByName(givenCouponRequest.getName())
                                        .orElseThrow(GivenCouponNotFoundException.CouponInfoNotFoundException::new);

        givenCouponRepository.save(this.toEntity(coupon, member));
    }

    /**
     * {@inheritDoc}
     *
     * @param memberInfo - 조회할 회원의 정보입니다.
     * @param pageable   - 요청 페이지 정보입니다.
     * @return 조회한 페이지 번호, 페이지의 데이터 수, 전체 페이지 수, 조회한 지급 쿠폰 목록을 담은 객체를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @Override
    public PageEntity<GivenCouponResponse> retrieveGivenCoupons(final MemberInfo memberInfo, final Pageable pageable) {
        Page<GivenCoupon> givenCoupons
            = givenCouponRepository.findByMemberId(memberInfo.getId(), pageable)
                                   .orElseThrow(GivenCouponNotFoundException::new);

        List<GivenCouponResponse> givenCouponList = givenCoupons.getContent().stream()
                                                                .map(this::checkAvailability)
                                                                .collect(Collectors.toUnmodifiableList());

        return new PageEntity<>(givenCoupons.getNumber(), givenCoupons.getSize(),
            givenCoupons.getTotalPages(), givenCouponList);
    }

    /**
     * 조회한 지급 쿠폰의 상태를 체크하여 상태 정보가 담긴 Dto 를 반환합니다.
     *
     * @param givenCoupons - 상태 체크할 지급 쿠폰의 Entity 입니다.
     * @return 상태 정보가 담긴 Dto 를 반환합니다.
     * @author 민아영
     */
    private GivenCouponResponse checkAvailability(final GivenCoupon givenCoupons) {

        CouponStatus status;
        Integer expiredDate = givenCoupons.getCoupon().getExpiredDate();
        LocalDateTime expirationPeriod = givenCoupons.getCreatedAt().plusDays(expiredDate);

        if (!usedCouponRepository.findAllByGivenCoupon(givenCoupons).isEmpty()) {
            status = USED;
        } else if (expirationPeriod.isBefore(LocalDateTime.now())) {
            status = EXPIRED;
        } else {
            status = VALID;
        }

        return this.toDto(givenCoupons, status.state(), expirationPeriod);
    }

}
