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
import java.time.LocalDateTime;

@Table(name = "product_inquires_post")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductInquiryPost {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "productNo")
    @ManyToOne
    @JoinColumn
    private Product product;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "is_secret")
    private Boolean isSecret;

    @Column(name = "admin_reply")
    private String adminReply;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Pk implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_inquiry_no")
        private Long productInquiryNo;

        @Column(name = "product_no")
        private Long productNo;

    }

}
