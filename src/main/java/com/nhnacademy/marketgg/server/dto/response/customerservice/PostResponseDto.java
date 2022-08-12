package com.nhnacademy.marketgg.server.dto.response.customerservice;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Query DSL 에서 게시글 상세정보의 값을 바인딩 하는 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class PostResponseDto {

    private final Long id;

    private final String categoryCode;

    private final String title;

    private final String content;

    private final String reason;

    private final String status;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

}
