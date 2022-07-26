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
import lombok.ToString;

@Table(name = "carts")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cart {

    @EmbeddedId
    private Pk id;

    @MapsId(value = "memberId")
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @MapsId(value = "productId")
    @ManyToOne
    @JoinColumn(name = "product_no")
    private Product product;

    @Column
    private Integer amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Cart(Member member, Product product, Integer amount) {
        this.id = new Pk(member.getId(), product.getId());
        this.member = member;
        this.product = product;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public void updateAmount(Integer amount) {
        this.amount = amount;
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "member_no")
        private Long memberId;

        @Column(name = "product_no")
        private Long productId;

    }

}
