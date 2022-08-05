package com.nhnacademy.marketgg.server.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니에 담긴 상품 목록 개체입니다.
 *
 * @author 공통
 * @version 1.0
 * @since 1.0
 */
@Table(name = "cart_products")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CartProduct {

    @EmbeddedId
    @NotNull
    private Pk pk;

    @MapsId(value = "cartId")
    @ManyToOne
    @JoinColumn(name = "cart_no")
    @NotNull
    private Cart cart;

    @MapsId(value = "productId")
    @ManyToOne
    @JoinColumn(name = "product_no")
    @NotNull
    private Product product;

    @Column
    @NotNull
    private Integer amount;

    @Column(name = "created_at")
    @NotNull
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
