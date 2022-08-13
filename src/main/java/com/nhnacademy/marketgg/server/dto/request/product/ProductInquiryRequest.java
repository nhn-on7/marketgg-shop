package com.nhnacademy.marketgg.server.dto.request.product;

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

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 50, message = "제목의 최대 글자 수는 50자입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    @Size(max = 200, message = "내용의 최대 글자 수는 200자입니다.")
    private String content;

    @NotNull
    private Boolean isSecret;

}
