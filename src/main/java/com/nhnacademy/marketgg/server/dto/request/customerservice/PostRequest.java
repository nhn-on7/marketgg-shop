package com.nhnacademy.marketgg.server.dto.request.customerservice;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글을 생성/수정 할 때 사용하는 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class PostRequest {

    @Schema(name = "카테고리 번호", description = "등록 또는 수정할 게시판의 카테고리의 식별번호입니다.", example = "701")
    @NotBlank
    @Size(min = 1, max = 6)
    private String categoryCode;

    @Schema(name = "게시글 제목", description = "입력한 게시글의 제목입니다.", example = "안녕 디지몬")
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @Schema(name = "게시글 내용", description = "입력한 게시글의 내용입니다.", example = "반갑네")
    @NotBlank
    @Size(min = 1)
    private String content;

    @Schema(name = "게시글 사유", description = "입력한 게시글의 사유입니다.", example = "배송")
    @NotBlank
    @Size(min = 1, max = 100)
    private String reason;

}
