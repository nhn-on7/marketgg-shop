package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "given_coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GivenCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "couponNo")
    @ManyToOne
    @JoinColumn
    private Coupon coupon;

    @MapsId(value = "memberNo")
    @ManyToOne
    @JoinColumn
    private Member member;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Pk implements Serializable {

        @Column(name = "coupon_no")
        private Long couponNo;

        @Column(name = "member_no")
        private Long memberNo;

    }

}
