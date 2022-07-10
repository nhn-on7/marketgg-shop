package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "order_products")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderProduct {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "orderNo")
    @ManyToOne
    @JoinColumn
    private Order order;

    @MapsId(value = "productNo")
    @ManyToOne
    @JoinColumn
    private Product product;

    @Column
    private String name;

    @Column
    private Long price;

    @Column
    private Integer amount;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "order_no")
        private Long orderNo;

        @Column(name = "product_no")
        private Long productNo;

    }

}
