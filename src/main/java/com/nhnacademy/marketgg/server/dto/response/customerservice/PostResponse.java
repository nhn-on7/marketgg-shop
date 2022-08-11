package com.nhnacademy.marketgg.server.dto.response.customerservice;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostResponse {

    private final Long id;

    private final String categoryCode;

    private final String title;

    private final String reason;

    private final String status;

    private final LocalDateTime createdAt;

}
