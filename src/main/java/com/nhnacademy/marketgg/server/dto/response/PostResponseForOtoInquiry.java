package com.nhnacademy.marketgg.server.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostResponseForOtoInquiry {

    private String postNo;

    private String title;

    private String content;

    private String reason;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CommentResponse> commentList;

    @Builder
    public PostResponseForOtoInquiry(PostResponseForOtoInquiry otoInquiry, List<CommentResponse> commentList) {
        this.postNo = otoInquiry.getPostNo();
        this.title = otoInquiry.getTitle();
        this.content = otoInquiry.getContent();
        this.reason = otoInquiry.getReason();
        this.status = otoInquiry.getStatus();
        this.createdAt = otoInquiry.getCreatedAt();
        this.updatedAt = otoInquiry.getUpdatedAt();
        this.commentList = commentList;
    }

}
