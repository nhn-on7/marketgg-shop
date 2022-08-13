package com.nhnacademy.marketgg.server.dto.response.product;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 한 회원의 상품 문의 조회 결과 DTO 입니다.
 */
@RequiredArgsConstructor
@Getter
public class ProductInquiryByMemberResponse {

    private final String uuid;

    private final Long productId;

    private final String title;

    private final String content;

    private final String adminReply;

    private final LocalDateTime createdAt;

}
