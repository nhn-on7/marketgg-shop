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
import javax.persistence.JoinColumns;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    @JoinColumn
    private Order order;

    @OneToOne
    @JoinColumns({
            @JoinColumn(referencedColumnName = "coupon_no"),
            @JoinColumn(referencedColumnName = "member_no")
    })
    private GivenCoupon givenCoupon;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Pk implements Serializable {

        @Column(name = "order_no")
        private Long orderNo;

        @Column(name = "coupon_no")
        private Long couponNo;

        @Column(name = "member_no")
        private Long memberNo;

    }

}
