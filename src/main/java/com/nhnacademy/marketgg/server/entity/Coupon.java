package com.nhnacademy.marketgg.server.entity;

import lombok.*;

import javax.persistence.*;

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
