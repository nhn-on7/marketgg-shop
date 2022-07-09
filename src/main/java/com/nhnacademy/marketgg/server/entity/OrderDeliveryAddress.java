package com.nhnacademy.marketgg.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "order_delivery_addresses")
@Entity
@NoArgsConstructor
@Getter
public class OrderDeliveryAddress {

    @Id
    @OneToOne
    @JoinColumn(name = "order_no")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "delivery_address_no")
    private DeliveryAddress deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "road_name_address")
    private String roadNameAddress;

    @Column(name = "detail_address")
    private String detailAddress;

}
