package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.coupon.CouponDto;
import java.time.LocalDateTime;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 쿠폰 개체입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
@Table(name = "coupons")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@DynamicUpdate
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

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void updateCoupon(final CouponDto couponRequest) {
        this.name = couponRequest.getName();
        this.type = couponRequest.getType();
        this.expiredDate = couponRequest.getExpiredDate();
        this.minimumMoney = couponRequest.getMinimumMoney();
        this.discountAmount = couponRequest.getDiscountAmount();
    }

    public void deleteCoupon() {
        this.deletedAt = LocalDateTime.now();
    }

}
