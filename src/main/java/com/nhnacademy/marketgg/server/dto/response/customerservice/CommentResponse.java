package com.nhnacademy.marketgg.server.dto.response.customerservice;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentResponse {

    private final String content;

    private final String name;

    private final LocalDateTime createdAt;

}
