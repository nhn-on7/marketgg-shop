package com.nhnacademy.marketgg.server.dto.response.customerservice;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 게시글 상세 정보를 받지만, 회원의 정보를 uuid 로 담는 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class PostResponseForReady {

    private final Long id;

    private final String categoryCode;

    private final String title;

    private final String content;

    private final String reason;

    private final String status;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final List<CommentReady> commentReady;

    /**
     * Query DSL 을 통해 받아온 DTO 값으로 원하는 Response DTO 를 생성합니다.
     *
     * @param dto -
     * @param commentList -
     */
    public PostResponseForReady(final PostResponseDto dto, final List<CommentReady> commentList) {
        this.id = dto.getId();
        this.categoryCode = dto.getCategoryCode();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.reason = dto.getReason();
        this.status = dto.getStatus();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.commentReady = commentList;
    }

}
