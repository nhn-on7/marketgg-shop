package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.OrderCreateRequest;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 개체입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@Table(name = "orders")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    @NotNull
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_no")
    @NotNull
    private Member member;

    @Column(name = "order_date")
    @NotNull
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    @NotNull
    private Long totalAmount;

    @Column(name = "order_status")
    @NotBlank
    @Size(min = 1, max = 10)
    private String orderStatus;

    @Column(name = "used_point")
    @NotNull
    private Integer usedPoint;

    /**
     * 주문 생성자입니다.
     *
     * @param member       - 주문을 한 회원 객체
     * @param orderRequest - 주문 요청 객체
     */
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
