package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "used_coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UsedCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "orderNo")
    @OneToOne
    @JoinColumn(name = "order_no")
    private Order order;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "coupon_no", referencedColumnName = "coupon_no", updatable = false, insertable = false),
            @JoinColumn(name = "member_no", referencedColumnName = "member_no", updatable = false, insertable = false)
    })
    private GivenCoupon givenCoupon;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "order_no")
        private Long orderNo;

        @Column(name = "coupon_no")
        private Long couponNo;

        @Column(name = "member_no")
        private Long memberNo;

    }

}
