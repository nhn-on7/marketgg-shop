package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.ProductInquiryReply;
import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import lombok.*;

import javax.persistence.*;
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
    @JoinColumn(name = "product_no")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_no")
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
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class Pk implements Serializable {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_inquiry_no")
        private Long productInquiryNo;

        @Column(name = "product_no")
        private Long productNo;

        public Pk(Long productNo) {
            this.productNo = productNo;
        }
    }

    public ProductInquiryPost(Product product, Member member, ProductInquiryRequest inquiryRequest) {
        this.pk = new Pk(product.getId());
        this.product = product;
        this.member = member;
        this.title = inquiryRequest.getTitle();
        this.content = inquiryRequest.getContent();
        this.isSecret = inquiryRequest.getIsSecret();
        this.createdAt = LocalDateTime.now();
    }

    public void updateInquiry(ProductInquiryReply inquiryReply) {
        this.adminReply = inquiryReply.getAdminReply();
    }

}
