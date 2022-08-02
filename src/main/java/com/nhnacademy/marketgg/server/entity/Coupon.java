package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 개체입니다.
 */
@Table(name = "coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Column(name = "coupon_no")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "쿠폰 이름이 유효하지 않습니다.")
    private String name;

    @Column
    @NotBlank
    private String type;

    @Column(name = "expired_date")
    @NotNull
    @Positive
    private Integer expiredDate;

    @Column(name = "minimum_money")
    @NotNull
    @Positive
    private Integer minimumMoney;

    @Column(name = "discount_amount")
    @NotNull
    @Positive
    private Double discountAmount;

    public Coupon(final CouponDto couponRequest) {
        this.name = couponRequest.getName();
        this.type = couponRequest.getType();
        this.expiredDate = couponRequest.getExpiredDate();
        this.minimumMoney = couponRequest.getMinimumMoney();
        this.discountAmount = couponRequest.getDiscountAmount();
    }

    public void updateCoupon(final CouponDto couponRequest) {
        this.name = couponRequest.getName();
        this.type = couponRequest.getType();
        this.expiredDate = couponRequest.getExpiredDate();
        this.minimumMoney = couponRequest.getMinimumMoney();
        this.discountAmount = couponRequest.getDiscountAmount();
    }
}
