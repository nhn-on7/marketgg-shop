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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_no")
    private Long id;

    @NotBlank(message = "쿠폰 이름이 유효하지 않습니다.")
    @Column
    private String name;

    @NotBlank
    @Column
    private String type;

    @Min(0)
    @Column(name = "expired_date")
    private Integer expiredDate;

    @Positive
    @Column(name = "minimum_money")
    private Integer minimumMoney;

    @NotNull
    @Column(name = "discount_amount")
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
