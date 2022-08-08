package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.Member;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 상품 문의 응답 DTO 입니다.
 */
@RequiredArgsConstructor
@Getter
public class ProductInquiryResponse {

    private final Member member;

    private final String title;

    private final String content;

    private final Boolean isSecret;

    private final String adminReply;

    private final LocalDateTime createdAt;

}
