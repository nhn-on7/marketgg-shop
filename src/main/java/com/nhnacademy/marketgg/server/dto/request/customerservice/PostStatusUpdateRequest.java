package com.nhnacademy.marketgg.server.dto.request.customerservice;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 1:1 문의의 상태를 수정하는데 쓰이는 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class PostStatusUpdateRequest {

    @Schema(name = "상태", description = "1:1 게시글의 상태 값입니다.", example = "답변 완료")
    @NotBlank
    @Size(min = 1)
    private String status;

}
