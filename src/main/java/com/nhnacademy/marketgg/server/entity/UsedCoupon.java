package com.nhnacademy.marketgg.server.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용 쿠폰 개체입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
@Table(name = "used_coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class UsedCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "orderId")
    @OneToOne
    @JoinColumn(name = "order_no")
    private Order order;

    @OneToOne
    @JoinColumn(name = "coupon_no", referencedColumnName = "coupon_no", updatable = false, insertable = false)
    @JoinColumn(name = "member_no", referencedColumnName = "member_no", updatable = false, insertable = false)
    private GivenCoupon givenCoupon;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "order_no")
        @NotNull
        private Long orderId;

        @Column(name = "coupon_no")
        @NotNull
        private Long couponId;

        @Column(name = "member_no")
        @NotNull
        private Long memberId;

    }

}
