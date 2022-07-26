package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.UsedCouponDto;
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

    @MapsId(value = "orderId")
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
        private Long orderId;

        @Column(name = "coupon_no")
        private Long couponId;

        @Column(name = "member_no")
        private Long memberId;

        public Pk(Long orderId, Long couponId, Long memberId) {
            this.orderId = orderId;
            this.couponId = couponId;
            this.memberId = memberId;
        }

    }

    public UsedCoupon(UsedCouponDto usedCouponDto, Order order, GivenCoupon givenCoupon) {
        this.pk = new Pk(usedCouponDto.getOrderId(), usedCouponDto.getCouponId(), usedCouponDto.getMemberId());
        this.order = order;
        this.givenCoupon = givenCoupon;
    }

}
