package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
import java.time.LocalDateTime;

@Table(name = "cart_products")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CartProduct {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "cartId")
    @ManyToOne
    @JoinColumn(name = "cart_no")
    private Cart cart;

    @MapsId(value = "productId")
    @ManyToOne
    @JoinColumn(name = "product_no")
    private Product product;

    @Column
    private Integer amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CartProduct(Cart cart, Product product, Integer amount) {
        this.pk = new Pk(cart.getId(), product.getId());
        this.cart = cart;
        this.product = product;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public void updateAmount(Integer amount) {
        this.amount = amount;
    }

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        private Long cartId;
        private Long productId;

    }

}
