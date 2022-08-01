package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Table(name = "coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// MEMO 1: Builder 로 Entity 에서 Dto 로 변환하는 생성자 MapStruct 가 만들어준다
@AllArgsConstructor()
@Builder
@Getter
// MEMO 1: Update entity 할 때 Setter 사용
@Setter
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
    @Positive
    private Integer expiredDate;

    @Column(name = "minimum_money")
    @Positive
    private Integer minimumMoney;

    @Column(name = "discount_amount")
    @NotNull
    private Double discountAmount;

    // MEMO 0: 생성자 주석함
    // public Coupon(final CouponRequest couponRequest) {
    //     this.name = couponRequest.getName();
    //     this.type = couponRequest.getType();
    //     this.expiredDate = couponRequest.getExpiredDate();
    //     this.minimumMoney = couponRequest.getMinimumMoney();
    //     this.discountAmount = couponRequest.getDiscountAmount();
    // }
    //
    // public void updateCoupon(final CouponRequest couponRequest) {
    //     this.name = couponRequest.getName();
    //     this.type = couponRequest.getType();
    //     this.expiredDate = couponRequest.getExpiredDate();
    //     this.minimumMoney = couponRequest.getMinimumMoney();
    //     this.discountAmount = couponRequest.getDiscountAmount();
    // }

}
