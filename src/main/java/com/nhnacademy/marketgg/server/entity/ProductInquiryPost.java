package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.product.ProductInquiryRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
    @NotNull(message = "제목은 필수 입력값입니다.")
    @Size(max = 50)
    private String title;

    @Column
    @NotNull(message = "내용은 필수 입력값입니다.")
    @Size(max = 200)
    private String content;

    @Column(name = "is_secret")
    @NotNull
    private Boolean isSecret;

    @Column(name = "admin_reply")
    private String adminReply;

    @Column(name = "created_at")
    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;

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

    public ProductInquiryPost(Product product, Member member, ProductInquiryRequest productInquiryRequest) {
        this.pk = new Pk(product.getId());
        this.product = product;
        this.member = member;
        this.title = productInquiryRequest.getTitle();
        this.content = productInquiryRequest.getContent();
        this.isSecret = productInquiryRequest.getIsSecret();
        this.createdDate = LocalDateTime.now();
    }

    public void updateInquiry(ProductInquiryRequest inquiryReply) {
        this.adminReply = inquiryReply.getAdminReply();
    }

}
