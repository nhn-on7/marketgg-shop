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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 찜 Entity 입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
@Table(name = "dibs")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Dib {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "memberId")
    @ManyToOne
    @JoinColumn(name = "member_no")
    @NotNull
    private Member member;

    @MapsId(value = "productId")
    @ManyToOne
    @JoinColumn(name = "product_no")
    @NotNull
    private Product product;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "member_no")
        private Long memberId;

        @Column(name = "product_no")
        private Long productId;

        public Pk(final Long memberId, final Long productId) {
            this.memberId = memberId;
            this.productId = productId;
        }

    }

    /**
     * 찜을 등록하기 위한 생성자입니다.
     *
     * @param member  - 찜을 등록하는 회원입니다.
     * @param product - 찜의 대상이 되는 상품입니다.
     * @since 1.0.0
     */
    public Dib(final Pk pk, final Member member, final Product product) {
        this.pk = pk;
        this.member = member;
        this.product = product;
        this.createdAt = LocalDateTime.now();
    }

}
