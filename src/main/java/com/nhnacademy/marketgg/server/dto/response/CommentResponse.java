package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CommentResponse {

    private final Long id;

    private final Long csPostId;

    private final Long memberId;

    private String content;

    private LocalDateTime createdAt;

}
