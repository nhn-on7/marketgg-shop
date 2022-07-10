package com.nhnacademy.marketgg.server.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "product_labels")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductLabel {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "productNo")
    @ManyToOne
    @JoinColumn
    private Product product;

    @MapsId(value = "labelNo")
    @ManyToOne
    @JoinColumn
    private Label label;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Pk implements Serializable {

        @Column(name = "product_no")
        private Long productNo;

        @Column(name = "label_no")
        private Long labelNo;

    }

}
