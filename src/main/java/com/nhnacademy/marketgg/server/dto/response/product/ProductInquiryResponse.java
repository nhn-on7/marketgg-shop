package com.nhnacademy.marketgg.server.dto.response.product;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 한 상품에 대한 상품 문의 조회 결과 DTO 입니다.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class ProductInquiryResponse {

    private final String uuid;

    private final Long productId;

    private final Long productInquiryNo;

    private final String productName;

    private final String title;

    private final String content;

    private final Boolean isSecret;

    private final String adminReply;

    private final LocalDateTime createdAt;

    private String name;

    public void memberName(final String name) {
        this.name = name;
    }

}
