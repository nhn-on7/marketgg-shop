package com.nhnacademy.marketgg.server.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 라벨 개체입니다.
 *
 * @author 공통
 * @version 1.0
 * @since 1.0
 */
@Table(name = "order_products")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderProduct {

    @EmbeddedId
    @NotNull
    private Pk pk;

    @MapsId(value = "orderNo")
    @ManyToOne
    @JoinColumn(name = "order_no")
    @NotNull
    private Order order;

    @MapsId(value = "productNo")
    @ManyToOne
    @JoinColumn(name = "product_no")
    @NotNull
    private Product product;

    @Column
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @Column
    @NotNull
    private Long price;

    @Column
    @NotNull
    private Integer amount;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "order_no")
        @NotNull
        private Long orderNo;

        @Column(name = "product_no")
        @NotNull
        private Long productNo;

        public Pk(Long orderId, Long productId) {
            this.orderNo = orderId;
            this.productNo = productId;
        }

    }

    // memo: 주문상품 생성자 만들고 order, product 받고 수량받아야함

}
