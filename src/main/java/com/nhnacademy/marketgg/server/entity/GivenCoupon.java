package com.nhnacademy.marketgg.server.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

/**
 * 지급 쿠폰 엔티티 입니다.
 * 쿠폰 id, 회원 id 를 받아서 지급 쿠폰을 생성합니다.
 *
 * @version 1.0.0
 */
@Table(name = "given_coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GivenCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "couponId")
    @ManyToOne
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;

    @MapsId(value = "memberNo")
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 지급쿠폰의 Pk 입니다.
     * couponId - 지급한 쿠폰의 id 입니다.
     * memberNo - 회원의 id 입니다.
     */
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "coupon_no")
        private Long couponId;

        @Column(name = "member_no")
        private Long memberNo;

        public Pk(Long couponId, Long memberNo) {
            this.couponId = couponId;
            this.memberNo = memberNo;
        }

    }

    public GivenCoupon(final Coupon coupon, final Member member) {
        this.pk = new Pk(coupon.getId(), member.getId());
        this.coupon = coupon;
        this.member = member;
        this.createdAt = LocalDateTime.now();
    }

    public static GivenCoupon test() {
        return new GivenCoupon();
    }

}
