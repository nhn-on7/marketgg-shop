package com.nhnacademy.marketgg.server.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

/**
 * 상품 문의 개체입니다.
 *
 * @author 공통
 * @version 1.0.0
 */
@Table(name = "product_inquires_post")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ProductInquiryPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_inquiry_no")
    private Long productInquiryNo;

    @ManyToOne
    @JoinColumn(name = "product_no")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 50)
    private String title;

    @Column
    @NotBlank(message = "내용은 필수 입력값입니다.")
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

    public void updateInquiry(String inquiryReply) {
        this.adminReply = inquiryReply;
    }

}
