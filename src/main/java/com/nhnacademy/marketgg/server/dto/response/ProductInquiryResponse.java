package com.nhnacademy.marketgg.server.dto.response;

import com.nhnacademy.marketgg.server.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

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
