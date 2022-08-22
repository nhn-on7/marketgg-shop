package com.nhnacademy.marketgg.server.dto.response.customerservice;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentResponse {

    @Schema(name = "댓글 내용", description = "1:1 문의 댓글의 내용입니다.", example = "싫다니깐요")
    private final String content;

    @Schema(name = "작성자", description = "1:1 문의 댓글의 작성자입니다.", example = "윤동열")
    private final String name;

    @Schema(name = "작성일자", description = "1:1 문의 댓글의 작성일자입니다.", example = "2020-07-10T15:00:00.000")
    private final LocalDateTime createdAt;

}
