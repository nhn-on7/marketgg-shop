package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "payments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_no")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_no")
    private Order order;

    @Column
    private Integer code;

    @Column(name = "pay_method")
    private String payMethod;

    @Column(name = "used_point")
    private Integer usedPoint;

    @Column(name = "pay_status")
    private String payStatus;

    @Column(name = "created_ts")
    private LocalDateTime createdTs;

    @Column(name = "pay_amount")
    private Long payAmount;

    @Column(name = "discount_amount")
    private Long discountAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
