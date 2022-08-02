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
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 라벨 개체입니다.
 *
 * @since 1.0.0
 */
@Table(name = "product_labels")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductLabel {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "productNo")
    @ManyToOne
    @JoinColumn(name = "product_no")
    private Product product;

    @MapsId(value = "labelNo")
    @ManyToOne
    @JoinColumn(name = "label_no")
    private Label label;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "product_no")
        private Long productNo;

        @Column(name = "label_no")
        private Long labelNo;

        public Pk(final Long productNo, final Long labelNo) {
            this.productNo = productNo;
            this.labelNo = labelNo;
        }
    }

    /**
     * 상품 라벨을 등록하기 위한 생성자입니다.
     *
     * @param pk      상품 라벨의 복합 pk입니다.
     * @param product 라벨에 해당하는 상품 입니다.
     * @param label   라벨입니다.
     * @since 1.0.0
     */
    public ProductLabel(final Pk pk, final Product product, final Label label) {
        this.pk = pk;
        this.product = product;
        this.label = label;
    }

}
