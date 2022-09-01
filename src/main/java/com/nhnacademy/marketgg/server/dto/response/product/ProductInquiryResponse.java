package com.nhnacademy.marketgg.server.dto.response.product;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 한 상품에 대한 상품 문의 조회 결과 DTO 입니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
@Builder
@Getter
public class ProductInquiryResponse {

    private String uuid;

    private Long productId;

    private Long productInquiryNo;

    private String productName;

    private String title;

    private String content;

    private Boolean isSecret;

    private String adminReply;

    private LocalDateTime createdAt;

    private String name;

    private Boolean isReadable;

    public void memberName(final String name) {
        this.name = name;
    }

    public void setIsReadable() {
        this.isReadable = true;
    }

}
