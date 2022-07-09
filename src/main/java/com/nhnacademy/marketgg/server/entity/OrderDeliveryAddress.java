package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "order_delivery_addresses")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDeliveryAddress implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "order_no")
    private Order order;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "delivery_address_no"),
            @JoinColumn(name = "member_no")
    })
    private DeliveryAddress deliveryAddress;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "road_name_address")
    private String roadNameAddress;

    @Column(name = "detail_address")
    private String detailAddress;

}
