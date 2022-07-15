package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

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

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "minimum_money")
    private Integer minimumMoney;

}
