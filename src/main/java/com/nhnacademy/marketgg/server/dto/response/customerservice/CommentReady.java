package com.nhnacademy.marketgg.server.dto.response.customerservice;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentReady {

    private final String content;

    private final String uuid;

    private final LocalDateTime createdAt;

}
