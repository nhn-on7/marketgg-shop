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
import java.time.LocalDateTime;

@Table(name = "dibs")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Dib {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "memberNo")
    @ManyToOne
    @JoinColumn
    private Member member;

    @MapsId(value = "productNo")
    @ManyToOne
    @JoinColumn
    private Product product;

    @Column
    private String memo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @Column(name = "member_no")
        private Long memberNo;

        @Column(name = "product_no")
        private Long productNo;

    }

}
