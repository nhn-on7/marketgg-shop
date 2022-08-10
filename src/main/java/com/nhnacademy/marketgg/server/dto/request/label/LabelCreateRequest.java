package com.nhnacademy.marketgg.server.dto.request.label;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 라벨을 생성하기 위한 요청 정보를 담고 있는 클래스입니다.
 *
 * @author 박세완
 * @version 1.0
 */
@NoArgsConstructor
@Getter
public class LabelCreateRequest {

    @NotNull
    private Long labelNo;

    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

}
