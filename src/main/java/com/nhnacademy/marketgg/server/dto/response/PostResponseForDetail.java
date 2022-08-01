package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostResponseForDetail {

    private final Long boardNo;

    private final String title;

    private final String content;

    private final String reason;

    private final String status;

    private final String createdAt;

    private final String updatedAt;

}
