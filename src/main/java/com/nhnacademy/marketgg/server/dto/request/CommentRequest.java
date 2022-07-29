package com.nhnacademy.marketgg.server.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentRequest {

    private Long csPostId;

    private Long memberId;

    private String content;

}
