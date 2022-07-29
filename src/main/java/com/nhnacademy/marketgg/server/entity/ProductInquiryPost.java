package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.ProductInquiryRequest;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "제목은 필수 입력값입니다.")
    @Length(max = 50)
    @Column
    private String title;

    @NotNull(message = "내용은 필수 입력값입니다.")
    @Length(max = 200)
    @Column
    private String content;

    @NotNull
    @Column(name = "is_secret")
    private Boolean isSecret;

    @Column(name = "admin_reply")
    private String adminReply;

    @CreatedDate
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

    public ProductInquiryPost(Product product, Member member, ProductInquiryRequest productInquiryRequest) {
        this.pk = new Pk(product.getId());
        this.product = product;
        this.member = member;
        this.title = productInquiryRequest.getTitle();
        this.content = productInquiryRequest.getContent();
        this.isSecret = productInquiryRequest.getIsSecret();
        this.createdAt = LocalDateTime.now();
    }

    public void updateInquiry(ProductInquiryRequest inquiryReply) {
        this.adminReply = inquiryReply.getAdminReply();
    }

}
