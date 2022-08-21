package com.nhnacademy.marketgg.server.dto.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 문의 DTO 입니다.
 */
@NoArgsConstructor
@Getter
public class ProductInquiryRequest {

    @Schema(title = "문의 제목", description = "상품 문의 글의 제목 입니다.", example = "밀키트 조리 시간")
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 50, message = "제목의 최대 글자 수는 50자입니다.")
    private String title;

    @Schema(title = "문의 내용", description = "상품 문의 글의 내용 입니다.", example = "밀키트의 조리 시간이 얼마나 걸릴까요?")
    @NotBlank(message = "내용은 필수 입력값입니다.")
    @Size(max = 200, message = "내용의 최대 글자 수는 200자입니다.")
    private String content;

    @Schema(title = "비밀글 여부", description = "상품 문의가 비밀글인지 여부를 나타냅니다.", example = "true")
    @NotNull
    private Boolean isSecret;

}
