package com.nhnacademy.marketgg.server.dto.response.customerservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CommentResponse {

    private final String content;

    // FIXME: 8월 1일 현재 memberId 로 email 을 받을 수 있는 방법이 없어 임시로 이렇게 해놨음 추후 수정 예정
    private final Long email;

    private final LocalDateTime createdAt;

}
