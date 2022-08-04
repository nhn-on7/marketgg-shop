package com.nhnacademy.marketgg.server.dto.response.customerservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CommentResponse {

    private final String content;

    private final String email;

    private final LocalDateTime createdAt;

}
