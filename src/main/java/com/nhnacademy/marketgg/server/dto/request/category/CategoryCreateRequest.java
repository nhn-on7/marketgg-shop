package com.nhnacademy.marketgg.server.dto.request.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리를 생성하기 위한 요청 정보를 담고 있는 클래스입니다.
 *
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class CategoryCreateRequest {

    @NotBlank
    @Size(min = 3, max = 6)
    private String categoryCode;

    @NotBlank
    @Size(min = 3, max = 3)
    private String categorizationCode;

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    private Integer sequence;

}
