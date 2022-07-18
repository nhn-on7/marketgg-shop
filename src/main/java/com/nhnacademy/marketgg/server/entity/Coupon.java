package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.CouponRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_no")
    private Long id;

    @Column
    private String name;

    @Column
    private String type;

    @Column(name = "expired_date")
    private Integer expiredDate;

    @Column(name = "minimum_money")
    private Integer minimumMoney;

    @Column(name = "discount_amount")
    private Double discountAmount;

    public Coupon(final CouponRequest couponRequest) {
        this.name = couponRequest.getName();
        this.type = couponRequest.getType();
        this.expiredDate = couponRequest.getExpiredDate();
        this.minimumMoney = couponRequest.getMinimumMoney();
    }

    public void updateCoupon(final CouponRequest couponRequest) {
        this.name = couponRequest.getName();
        this.type = couponRequest.getType();
        this.expiredDate = couponRequest.getExpiredDate();
        this.minimumMoney = couponRequest.getMinimumMoney();
    }

}
