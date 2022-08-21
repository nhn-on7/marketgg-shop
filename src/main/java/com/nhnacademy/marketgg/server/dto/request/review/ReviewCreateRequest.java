package com.nhnacademy.marketgg.server.dto.request.review;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewCreateRequest {

    @Schema(title = "내용", description = "후기의 내용", example = "포카칩 맛있어요 다음에 또 사먹을게요")
    @Size(min = 10, max = 300, message = "후기 내용은 10자 이상, 300자 이하만 가능합니다.")
    private String content;

    @Schema(title = "별점", description = "후기의 별점", example = "5")
    @Max(value = 5, message = "평점은 5를 초과할 수 없습니다.")
    @Min(value = 1, message = "평점은 1 미만일 수 없습니다.")
    private Long rating;

}
