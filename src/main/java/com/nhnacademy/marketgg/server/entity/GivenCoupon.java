package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.GivenCouponRequest;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "given_coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GivenCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "couponNo")
    @ManyToOne
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;

    @MapsId(value = "memberNo")
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "coupon_no")
        private Long couponNo;

        @Column(name = "member_no")
        private Long memberNo;

    }

    public GivenCoupon(final Coupon coupon, final Member member,
                       final Long memberId, final GivenCouponRequest givenCouponRequest) {
        this.pk = new Pk(givenCouponRequest.getCouponNo(), memberId);
        this.coupon = coupon;
        this.member = member;
        this.createdAt = LocalDateTime.now();
    }

}
