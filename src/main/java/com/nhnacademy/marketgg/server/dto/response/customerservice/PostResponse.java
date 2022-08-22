package com.nhnacademy.marketgg.server.dto.response.customerservice;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostResponse {

    @Schema(name = "게시글 번호", description = "게시글의 식별번호입니다.", example = "1")
    private final Long id;

    @Schema(name = "카테고리 번호", description = "게시글이 해당하는 카테고리의 식별번호입니다.", example = "701")
    private final String categoryCode;

    @Schema(name = "게시글 제목", description = "게시글의 제목입니다.", example = "안녕 디지몬")
    private final String title;

    @Schema(name = "게시글 사유", description = "게시글의 사유입니다.", example = "배송")
    private final String reason;

    @Schema(name = "게시글 상태", description = "게시글의 상태입니다.", example = "답변 완료")
    private final String status;

    @Schema(name = "작성일자", description = "게시글의 작성일자입니다.", example = "2020-07-10T15:00:00.000")
    private final LocalDateTime createdAt;

}
