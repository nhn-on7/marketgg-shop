package com.nhnacademy.marketgg.server.dto.request.review;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewUpdateRequest {

    @Schema(title = "후기번호", description = "후기번호", example = "1")
    private Long reviewId;

    @Schema(title = "자원번호", description = "자원번호", example = "1")
    private Long assetId;

    @Schema(title = "내용", description = "후기의 내용", example = "포카칩 별로에요 다음에 안먹을래요")
    @Size(max = 300, message = "후기 내용은 300자를 넘을 수 없습니다.")
    private String content;

    @Schema(title = "별점", description = "후기의 별점", example = "3")
    @Max(value = 5, message = "평점은 5를 초과할 수 없습니다.")
    @Min(value = 1, message = "평점은 1 미만일 수 없습니다.")
    private Long rating;

}
