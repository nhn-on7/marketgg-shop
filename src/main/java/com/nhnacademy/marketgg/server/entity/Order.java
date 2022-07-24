package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "orders")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @OneToOne
    @JoinColumn(name = "crowdfunding_no")
    private Crowdfunding crowdfunding;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "used_point")
    private Integer usedPoint;

    public static Order test() {
        return new Order();
    }

}
