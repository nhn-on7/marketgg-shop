package com.nhnacademy.marketgg.server.dto.request.product;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductInquiryReplyRequest {

    @Schema(title = "문의 답글", description = "상품 문의 글의 답글 내용 입니다.", example = "조리시간은 15~20 분 정도 입니다.")
    @NotBlank
    @Size(min = 10, max = 300, message = "문의 답변 내용은 10자 이상, 300자 이하만 가능합니다.")
    private String adminReply;

}
