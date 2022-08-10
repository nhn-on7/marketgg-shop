package com.nhnacademy.marketgg.server.dto.request.customerservice;

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

    @NotBlank
    @Size(min = 1, max = 6)
    private String categoryCode;

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank
    @Size(min = 1)
    private String content;

    @NotBlank
    @Size(min = 1, max = 100)
    private String reason;

}
