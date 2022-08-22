package com.nhnacademy.marketgg.server.dto.response.label;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LabelRetrieveResponse {

    @Schema(name = "라벨 번호", description = "라벨의 식별번호입니다.", example = "1")
    private final Long labelNo;

    @Schema(name = "라벨 이름", description = "라벨의 이름입니다.", example = "10% 할인")
    private final String name;

}
