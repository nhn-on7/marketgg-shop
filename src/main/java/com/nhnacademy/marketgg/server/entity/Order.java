package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.OrderCreateRequest;
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

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "used_point")
    private Integer usedPoint;

    public Order(Member member, OrderCreateRequest orderRequest) {
        this.member = member;
        this.orderDate = LocalDateTime.now();
        this.totalAmount = orderRequest.getTotalAmount();
        this.orderStatus = orderRequest.getOrderStatus();
        this.usedPoint = orderRequest.getUsedPoint();
    }
    
    public static Order test() {
        return new Order();
    }

}
