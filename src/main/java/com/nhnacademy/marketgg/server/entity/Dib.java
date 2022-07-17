package com.nhnacademy.marketgg.server.entity;

import com.nhnacademy.marketgg.server.dto.request.DibCreateRequest;
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

/**
 * 찜 Entity 입니다.
 *
 * @version 1.0.0
 */
@Table(name = "dibs")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Dib {

    @EmbeddedId
    private Pk pk;

    @MapsId(value = "memberNo")
    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @MapsId(value = "productNo")
    @ManyToOne
    @JoinColumn(name = "product_no")
    private Product product;

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

        public Pk(final Long memberNo, final Long productNo) {
            this.memberNo = memberNo;
            this.productNo = productNo;
        }

    }

    /**
     * 찜을 등록하기 위한 생성자입니다.
     *
     * @param dibCreateRequest - 찜을 등록하기 위한 DTO 입니다.
     * @param member - 찜을 등록하는 회원입니다.
     * @param product - 찜의 대상이 되는 상품입니다.
     *
     * @since 1.0.0
     */
    public Dib(final DibCreateRequest dibCreateRequest, final Member member, final Product product) {
        this.member = member;
        this.product = product;
        this.createdAt = dibCreateRequest.getCreatedAt();
    }

}
