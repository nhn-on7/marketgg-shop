package com.nhnacademy.marketgg.server.dto.request.label;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
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

    @Schema(name = "라벨 이름", description = "등록할 라벨의 이름입니다.", example = "10% 할인")
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

}
